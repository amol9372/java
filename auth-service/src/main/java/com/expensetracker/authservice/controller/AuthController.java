package com.expensetracker.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.authservice.dto.UserSigninRequest;
import com.expensetracker.authservice.service.AuthService;

@RestController
public class AuthController {
 
	@Autowired
	private AuthService authService;
	
	@PostMapping("login")
	public void authenticateUser(@RequestBody UserSigninRequest userSigninRequest) {
		authService.AuthenticateUser(userSigninRequest);
	}
   	
	
}
