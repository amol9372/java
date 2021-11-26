package com.expensetracker.userservice.request;

public class CognitoSignInUserRequest {
	
	private String email;
	private String userName;
	private String password;

	public static class CognitoSignInUserBuilder {
		
		private final CognitoSignInUserRequest cognitoSignInUser;
		
		public CognitoSignInUserBuilder() {
			this.cognitoSignInUser = new CognitoSignInUserRequest();
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
        
        public CognitoSignInUserRequest build() {
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
