package com.expensetracker.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.authservice.dto.UserSigninRequest;
import com.expensetracker.authservice.dto.UserSigninResponse;
import com.expensetracker.authservice.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("login")
	public ResponseEntity<UserSigninResponse> authenticateUser(@RequestBody UserSigninRequest userSigninRequest) {

		var userSigninResponse = authService.authenticateUser(userSigninRequest);
		return ResponseEntity.status(userSigninResponse.getHttpStatus()).body(userSigninResponse);
	}

	@GetMapping("sample")
	public ResponseEntity<String> sample() {
		return ResponseEntity.status(200).body("Success Response");
	}

}
