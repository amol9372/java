package com.expensetracker.userservice.response;

import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;

public class AuthResponse {

	private AdminInitiateAuthResult adminInitiateAuthResult;
	private Boolean isAuthenticated;
	private String errorResponse;
	
	public AdminInitiateAuthResult getAdminInitiateAuthResult() {
		return adminInitiateAuthResult;
	}
	public void setAdminInitiateAuthResult(AdminInitiateAuthResult adminInitiateAuthResult) {
		this.adminInitiateAuthResult = adminInitiateAuthResult;
	}
	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}
	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	public String getErrorResponse() {
		return errorResponse;
	}
	public void setErrorResponse(String errorResponse) {
		this.errorResponse = errorResponse;
	}

	

}
