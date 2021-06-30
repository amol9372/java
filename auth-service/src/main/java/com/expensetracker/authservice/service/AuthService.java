package com.expensetracker.authservice.service;

import java.util.Objects;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.model.InternalErrorException;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.PasswordResetRequiredException;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.expensetracker.authservice.config.CognitoAuthUtils;
import com.expensetracker.authservice.config.JwtUtil;
import com.expensetracker.authservice.dto.AppToken;
import com.expensetracker.authservice.dto.AuthResponse;
import com.expensetracker.authservice.dto.CognitoSignInUser;
import com.expensetracker.authservice.dto.CognitoSignInUser.CognitoSignInUserBuilder;
import com.expensetracker.authservice.dto.UserDomain;
import com.expensetracker.authservice.dto.UserSigninRequest;
import com.expensetracker.authservice.dto.UserSigninResponse;

@Service
public class AuthService {

	@Autowired
	private JwtUtil jwtUtil;

	public UserSigninResponse AuthenticateUser(UserSigninRequest signinRequest) {

		var userSigninResponse = new UserSigninResponse();

		CognitoSignInUser cognitoSignInUser = new CognitoSignInUserBuilder().withUsername(signinRequest.getUserName())
				.withPassword(signinRequest.getPassword()).build();

		var authResult = getAuthenticationResult(cognitoSignInUser);

		if (Objects.nonNull(authResult.getAdminInitiateAuthResult())) {

			var adminiInitiateAuthResult = authResult.getAdminInitiateAuthResult();

			// check for challenge
			if (Objects.nonNull(adminiInitiateAuthResult.getChallengeName())) {
				userSigninResponse.setErrorMessage(adminiInitiateAuthResult.getChallengeName());
				userSigninResponse.setHttpStatus(HttpStatus.SC_FORBIDDEN);
			} else {

				// get user detail from DB
				UserDomain userDomain = new UserDomain.UserBuilder().withEmail("amolsingh9372@gmail.com")
						.withName("Amol Singh").withUserName("amol9372").build();

				//var accessToken = jwtUtil.createToken(userDomain);
				var appTokens = new AppToken();
				appTokens.setAccesToken(authResult.getAdminInitiateAuthResult().getAuthenticationResult().getAccessToken());
				appTokens.setIdentityToken(authResult.getAdminInitiateAuthResult().getAuthenticationResult().getIdToken());
				userSigninResponse.setTokens(appTokens);
				userSigninResponse.setHttpStatus(HttpStatus.SC_OK);
			}

		} else {
			userSigninResponse.setErrorMessage(authResult.getErrorResponse());
			userSigninResponse.setHttpStatus(HttpStatus.SC_UNAUTHORIZED);
		}

		return userSigninResponse;

	}

	private AuthResponse getAuthenticationResult(CognitoSignInUser cognitoSignInUser) {

		var authResponse = new AuthResponse();

		try {
			var adminInitiateAuthResult = CognitoAuthUtils.authenticateUser(cognitoSignInUser);
			authResponse.setAdminInitiateAuthResult(adminInitiateAuthResult);

		} catch (ResourceNotFoundException e) {
			authResponse.setErrorResponse(e.getMessage());
			
		} catch (NotAuthorizedException e) {
			authResponse.setErrorResponse(e.getMessage());			

		} catch (UserNotFoundException e) {
			authResponse.setErrorResponse(e.getMessage());	

		} catch (InternalErrorException e) {
			authResponse.setErrorResponse(e.getMessage());
			
		} catch(PasswordResetRequiredException e) {
			authResponse.setErrorResponse(e.getMessage());			
		}	

		return authResponse;
	}

}
