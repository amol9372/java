package org.practice.springbootjwt.controller;

import java.security.Principal;

import org.practice.springbootjwt.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
    @Autowired
    private JwtUtil jwtUtil;
	
	
	@GetMapping("/login")
	public String getUser() {
		return jwtUtil.createToken();
	}
	
	@GetMapping("/sample")
	public String getSample(Principal principal) {
		System.out.println(principal.getName());
		return "sample resource";
	}

}
