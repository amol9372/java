package com.expensetracker.trackerapp.entities.app;

public class Users {

  private String userId;
  private String username;
  private String email;
  private Double owes;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Double getOwes() {
    return owes;
  }

  public void setOwes(Double owes) {
    this.owes = owes;
  }
}
