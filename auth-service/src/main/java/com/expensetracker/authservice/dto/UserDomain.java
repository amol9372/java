package com.expensetracker.authservice.dto;

public class UserDomain {
	
	private Integer id;
	
	private String name;

	private String email;

	private String userName;

	private String gender;

	private String address;

	private String phoneNo;

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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public static class UserBuilder {
		
		public UserDomain userDomain;
		
		public UserBuilder() {
			userDomain = new UserDomain();
		}
		
		public UserBuilder withId(Integer id) {
			userDomain.setId(id);
			return this;
		}
		
		public UserBuilder withUserName(String userName) {
			userDomain.setUserName(userName);
			return this;
		}
		
		public UserBuilder withEmail(String email) {
			userDomain.setEmail(email);
			return this;
		}
		
		public UserBuilder withName(String name) {
			userDomain.setName(name);
			return this;
		}
		
		public UserBuilder withAddress(String address) {
			userDomain.setAddress(address);
			return this;
		}
		
		public UserBuilder withGender(String gender) {
			userDomain.setGender(gender);
			return this;
		}
		
		public UserDomain build() {
			return userDomain;
		}

		
		
	}

}
