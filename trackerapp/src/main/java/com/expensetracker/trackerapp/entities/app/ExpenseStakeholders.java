package com.expensetracker.trackerapp.entities.app;

import java.util.List;

/**
 * @author amol.singh
 */
public class ExpenseStakeholders {

  private String paidBy;
  private List<Users> users;

  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(String paidBy) {
    this.paidBy = paidBy;
  }

  public List<Users> getUsers() {
    return users;
  }

  public void setUsers(List<Users> users) {
    this.users = users;
  }
}
