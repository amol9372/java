package com.expensetracker.userservice.service;

import com.expensetracker.userservice.entities.model.UserEntity;
import com.expensetracker.userservice.request.UserSigninRequest;
import com.expensetracker.userservice.response.UserSigninResponse;
import org.springframework.stereotype.Service;

import com.expensetracker.userservice.request.ConfirmCognitoSignUpRequest;
import com.expensetracker.userservice.request.ConfirmSignupResponse;
import com.expensetracker.userservice.request.UserSignupResponse;
import com.expensetracker.userservice.request.UserSignupRequest;

@Service
public interface UserService {

	UserSignupResponse createUser(UserSignupRequest signupRequest);

	ConfirmSignupResponse confirmSignUp(ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest);

	UserSigninResponse authenticateUser(UserSigninRequest request);

	UserEntity getUserDetails(String username);

}
