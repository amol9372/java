package com.expensetracker.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class UserSigninResponse {

	private AppToken tokens;
	private String errorMessage;
	private Integer httpStatus;

	public AppToken getTokens() {
		return tokens;
	}

	public void setTokens(AppToken tokens) {
		this.tokens = tokens;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	

}
