package com.expensetracker.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class AuthenticationFilter implements GlobalFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private RouterValidator routerValidator;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (routerValidator.isSecured.test(request)) {
      if (this.isAuthMissing(request)) {
        return this.onError(exchange, Constants.AUTH_HEADER_MISSING);
      }

      final String token = this.getAuthHeader(request);
      var jwsClaims = jwtUtil.validateToken(token);

      if (jwsClaims.isEmpty()) {
        return this.onError(exchange, Constants.AUTH_HEADER_INVALID);
      }

      this.populateRequestWithHeaders(exchange, jwsClaims.get());
    }
    return chain.filter(exchange);
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return response.setComplete();
  }

  private String getAuthHeader(ServerHttpRequest request) {
    return request.getHeaders().getOrEmpty("Authorization").get(0);
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey("Authorization");
  }

  private void populateRequestWithHeaders(ServerWebExchange exchange, Jws<Claims> jwsClaims) {
    exchange.getRequest().mutate().header("id", String.valueOf(jwsClaims.getBody().get("id")))
        .header("role", String.valueOf(jwsClaims.getBody().get("role"))).build();
  }

}
