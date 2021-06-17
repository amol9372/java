package com.expensetracker.authservice.dto;

import com.expensetracker.authservice.config.Field;

public class CognitoSignInUser {
	
	@Field(name = "email")
	private String email;
	
	@Field(name = "username")
	private String userName;
	
	@Field(name = "password")
	private String password;

	public static class CognitoSignInUserBuilder {
		
		private CognitoSignInUser cognitoSignInUser;
		
		public CognitoSignInUserBuilder() {
			this.cognitoSignInUser = new CognitoSignInUser();
		}
		
        public CognitoSignInUserBuilder withEmail(String email) {
        	cognitoSignInUser.setEmail(email);
        	return this;
        }
        
        public CognitoSignInUserBuilder withPassword(String password) {
        	cognitoSignInUser.setPassword(password);
        	return this;
        }
        
        public CognitoSignInUserBuilder withUsername(String username) {
        	cognitoSignInUser.setUserName(username);
        	return this;
        }
        
        public CognitoSignInUser build() {
        	return cognitoSignInUser;
        }
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
