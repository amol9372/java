package com.expensetracker.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.userservice.model.ConfirmCognitoSignUpRequest;
import com.expensetracker.userservice.model.ConfirmSignupResponse;
import com.expensetracker.userservice.model.UserSignupRequest;
import com.expensetracker.userservice.model.UserSignupResponse;
import com.expensetracker.userservice.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("register")
	public ResponseEntity<UserSignupResponse> registerUser(@RequestBody UserSignupRequest signupRequest) {

		var signUpResponse = userService.signupUser(signupRequest);
		return ResponseEntity.status(signUpResponse.getStatus()).body(signUpResponse);
	}

	@PostMapping("confirm-signup")
	public ResponseEntity<ConfirmSignupResponse> confirmSignUp(
			@RequestBody ConfirmCognitoSignUpRequest confirmSignUpRequest) {
		var confirmSignupResponse = userService.confirmSignUp(confirmSignUpRequest);
		return ResponseEntity.status(confirmSignupResponse.getHttpStatus()).body(confirmSignupResponse);
	}

	@GetMapping("sample")
	public ResponseEntity<String> sample() {
		return ResponseEntity.status(200).body("Success Response :: User Service");
	}

}
