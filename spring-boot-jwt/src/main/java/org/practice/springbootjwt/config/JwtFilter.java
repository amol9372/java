package org.practice.springbootjwt.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.practice.springbootjwt.jwt.JwtUtil;
import org.practice.springbootjwt.model.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// write separate method to filter out permitted URLs
		if(request.getRequestURL().toString().contains("login")) {
			filterChain.doFilter(request, response);
		}
		
		var requestHeader = request.getHeader(AUTHORIZATION);

		Optional.ofNullable(requestHeader).orElseThrow(() -> new MissingRequestHeaderException(AUTHORIZATION, null));

		if (requestHeader.startsWith("Bearer")) {

			var jwtToken = requestHeader.substring(7);
			var jwtClaims = jwtUtil.validateToken(jwtToken);
			String userName = (String) jwtClaims.getBody().get("name");

			MyUserDetails myUserDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(userName);

			if (!ObjectUtils.isEmpty(myUserDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						myUserDetails, null, myUserDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
			filterChain.doFilter(request, response);

		}

	}

}
