package com.expensetracker.userservice.request;

import com.expensetracker.userservice.config.Field;

public class CognitoSignupUser {

	@Field(name = "name", required = true)
	private String name;

	@Field(name = "email", required = true)
	private String email;

	@Field(name = "password", primary = true)
	private String password;

	@Field(name = "username", primary = true)
	private String userName;

	@Field(name = "gender", required = true)
	private String gender;

	@Field(name = "address")
	private String address;

	@Field(name = "phone_number", required = true)
	private String phoneNo;

	public CognitoSignupUser(String userName, String password, String email) {
		super();
		this.email = email;
		this.password = password;
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public static class CognitoUserBuilder {
		
		public CognitoSignupUser cognitoSignupUser;
		
		public CognitoUserBuilder(String username, String password, String email) {
			cognitoSignupUser = new CognitoSignupUser(username, password, email);
		}
		
		public CognitoUserBuilder withUserName(String userName) {
			cognitoSignupUser.setUserName(userName);
			return this;
		}
		
		public CognitoUserBuilder withEmail(String email) {
			cognitoSignupUser.setEmail(email);
			return this;
		}
		
		public CognitoUserBuilder withPassword(String password) {
			cognitoSignupUser.setPassword(password);
			return this;
		}
		
		public CognitoUserBuilder withName(String name) {
			cognitoSignupUser.setName(name);
			return this;
		}
		
		public CognitoUserBuilder withAddress(String address) {
			cognitoSignupUser.setAddress(address);
			return this;
		}
		
		public CognitoUserBuilder withGender(String gender) {
			cognitoSignupUser.setGender(gender);
			return this;
		}

		public CognitoUserBuilder withPhoneNo(String phoneNo) {
			cognitoSignupUser.setPhoneNo(phoneNo);
			return this;
		}
		
		public CognitoSignupUser build() {
			return cognitoSignupUser;
		}

		
		
	}

}
