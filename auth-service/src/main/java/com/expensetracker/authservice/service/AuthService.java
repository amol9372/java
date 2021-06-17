package com.expensetracker.authservice.service;

import org.springframework.stereotype.Service;

import com.expensetracker.authservice.config.CognitoUtils;
import com.expensetracker.authservice.dto.CognitoSignInUser;
import com.expensetracker.authservice.dto.CognitoSignInUser.CognitoSignInUserBuilder;
import com.expensetracker.authservice.dto.UserSigninRequest;

@Service
public class AuthService {

	public void AuthenticateUser(UserSigninRequest signinRequest) {

		CognitoSignInUser cognitoSignInUser = new CognitoSignInUserBuilder().withUsername(signinRequest.getUserName())
				.withPassword(signinRequest.getPassword()).build();

		CognitoUtils.authenticateUser(cognitoSignInUser);
		
	}

}
