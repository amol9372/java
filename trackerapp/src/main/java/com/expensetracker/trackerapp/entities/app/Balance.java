package com.expensetracker.trackerapp.entities.app;

public class Balance {

  private Double owes;
  private Double gets;
  private Users user;

  public Double getOwes() {
    return owes;
  }

  public void setOwes(Double owes) {
    this.owes = owes;
  }

  public Double getGets() {
    return gets;
  }

  public void setGets(Double gets) {
    this.gets = gets;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }
}
