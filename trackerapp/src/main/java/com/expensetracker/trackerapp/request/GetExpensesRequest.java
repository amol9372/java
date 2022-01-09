package com.expensetracker.trackerapp.request;

public class GetExpensesRequest {

  private String userId;
  private String groupId;
  private Object startKey;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public Object getStartKey() {
    return startKey;
  }

  public void setStartKey(Object startKey) {
    this.startKey = startKey;
  }
}
