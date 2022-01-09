package com.expensetracker.trackerapp.response;

public class CreateExpenseResponse {

  private String expenseId;
  private String message;

  public CreateExpenseResponse(String expenseId, String message) {
    this.expenseId = expenseId;
    this.message = message;
  }

  public String getExpenseId() {
    return expenseId;
  }

  public void setExpenseId(String expenseId) {
    this.expenseId = expenseId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
