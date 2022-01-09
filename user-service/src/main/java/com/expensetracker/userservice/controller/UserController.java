package com.expensetracker.userservice.controller;

import com.expensetracker.userservice.request.UserSigninRequest;
import com.expensetracker.userservice.response.UserSigninResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.userservice.request.ConfirmCognitoSignUpRequest;
import com.expensetracker.userservice.request.ConfirmSignupResponse;
import com.expensetracker.userservice.request.UserSignupRequest;
import com.expensetracker.userservice.request.UserSignupResponse;
import com.expensetracker.userservice.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("register")
	public ResponseEntity<UserSignupResponse> registerUser(@RequestBody UserSignupRequest signupRequest) {

		var signUpResponse = userService.createUser(signupRequest);
		return ResponseEntity.status(signUpResponse.getStatus()).body(signUpResponse);
	}

	@PostMapping("confirm-signup")
	public ResponseEntity<ConfirmSignupResponse> confirmSignUp(
			@RequestBody ConfirmCognitoSignUpRequest confirmSignUpRequest) {
		var confirmSignupResponse = userService.confirmSignUp(confirmSignUpRequest);
		return ResponseEntity.status(confirmSignupResponse.getHttpStatus()).body(confirmSignupResponse);
	}

	@GetMapping("{username}")
	public void getUserDetails(@PathVariable String username){

	}

	@GetMapping("sample")
	public ResponseEntity<String> sample()  {
		return ResponseEntity.status(200).body("Success Response :: User Service");
	}

	@PostMapping("login")
	public ResponseEntity<UserSigninResponse> authenticateUser(@RequestBody UserSigninRequest request) {

		var response = userService.authenticateUser(request);
		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}

}
