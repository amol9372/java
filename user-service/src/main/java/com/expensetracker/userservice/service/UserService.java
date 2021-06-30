package com.expensetracker.userservice.service;

import java.util.Objects;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.expensetracker.userservice.config.CognitoUserUtils;
import com.expensetracker.userservice.model.CognitoSignUpResponse;
import com.expensetracker.userservice.model.CognitoSignupUser;
import com.expensetracker.userservice.model.ConfirmCognitoSignUpRequest;
import com.expensetracker.userservice.model.ConfirmSignupResponse;
import com.expensetracker.userservice.model.UserSignupResponse;
import com.expensetracker.userservice.model.UserSignupRequest;

@Service
public class UserService {

	private static final String USER_CREATED_NOT_CONFIRMED = "User created successfully, Please confirm the signup process via email";

	public UserSignupResponse signupUser(UserSignupRequest signupRequest) {

		var userSignUpResponse = new UserSignupResponse();

		CognitoSignupUser cognitoSignupUser = new CognitoSignupUser.CognitoUserBuilder(signupRequest.getUsername(),
				signupRequest.getPassword(), signupRequest.getEmail())
						.withName(signupRequest.getName())
						.withGender(signupRequest.getGender()).build();

		var signupResult = getSignUpResponse(cognitoSignupUser);

		if (Objects.nonNull(signupResult.getSignUpResult())) {

			// user is created in the user pool
			userSignUpResponse.setUserConfirmed(signupResult.getSignUpResult().getUserConfirmed());
			userSignUpResponse.setMessage(USER_CREATED_NOT_CONFIRMED);
			userSignUpResponse.setStatus(HttpStatus.SC_CREATED);

		} else {
			userSignUpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
			userSignUpResponse.setMessage(signupResult.getErrorMessage());
		}

		return userSignUpResponse;

	}

	public ConfirmSignupResponse confirmSignUp(ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest) {
		var result = getConfirmSignUpResponse(confirmCognitoSignUpRequest);
		return result;
	}

	private CognitoSignUpResponse getSignUpResponse(CognitoSignupUser cognitoSignupUser) {

		var cognitoSignupResponse = new CognitoSignUpResponse();

		try {
			var result = CognitoUserUtils.signUpUser(cognitoSignupUser);
			cognitoSignupResponse.setSignUpResult(result);
		} catch (Exception e) {
			cognitoSignupResponse.setErrorMessage(e.getMessage());
		}

		return cognitoSignupResponse;
	}

	private ConfirmSignupResponse getConfirmSignUpResponse(ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest) {
		var result = new ConfirmSignupResponse();
		try {
			CognitoUserUtils.confirmSignupUser(confirmCognitoSignUpRequest.getUsername(),
					confirmCognitoSignUpRequest.getConfirmationCode());
			result.setHttpStatus(HttpStatus.SC_OK);
			result.setMessage("User is confirmed, please login now");
		} catch (Exception e) {
			result.setHttpStatus(HttpStatus.SC_BAD_REQUEST);
			result.setMessage(e.getMessage());
		}

		return result;
	}

}
