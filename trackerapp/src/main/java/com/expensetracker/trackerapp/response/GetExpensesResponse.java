package com.expensetracker.trackerapp.response;

import com.expensetracker.trackerapp.entities.app.Expense;
import java.util.List;

public class GetExpensesResponse {

  private List<Expense> expenses;
  private String message;
  private NextToken nextToken;

  public List<Expense> getExpenses() {
    return expenses;
  }

  public void setExpenses(List<Expense> expenses) {
    this.expenses = expenses;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public NextToken getNextToken() {
    return nextToken;
  }

  public void setNextToken(NextToken nextToken) {
    this.nextToken = nextToken;
  }

  public static class GetExpensesResponseBuilder {

    private GetExpensesResponse getExpensesResponse = new GetExpensesResponse();

    public GetExpensesResponseBuilder withExpenses(List<Expense> expenses) {
      getExpensesResponse.setExpenses(expenses);
      return this;
    }

    public GetExpensesResponseBuilder withMessage(String message) {
      getExpensesResponse.setMessage(message);
      return this;
    }

    public GetExpensesResponseBuilder withNextToken(NextToken token) {
      getExpensesResponse.setNextToken(token);
      return this;
    }

    public GetExpensesResponse build() {
      return getExpensesResponse;
    }
  }
}
