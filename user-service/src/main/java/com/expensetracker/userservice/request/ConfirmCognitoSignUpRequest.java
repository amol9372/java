package com.expensetracker.userservice.request;

public class ConfirmCognitoSignUpRequest {

	private String username;
	private String confirmationCode;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

}
