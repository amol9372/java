package com.expensetracker.userservice.model;

import com.amazonaws.services.cognitoidp.model.SignUpResult;

public class CognitoSignUpResponse {

	private SignUpResult signUpResult;
	private String errorMessage;

	public SignUpResult getSignUpResult() {
		return signUpResult;
	}

	public void setSignUpResult(SignUpResult signUpResult) {
		this.signUpResult = signUpResult;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
