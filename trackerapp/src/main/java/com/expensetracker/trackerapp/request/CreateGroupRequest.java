package com.expensetracker.trackerapp.request;

import java.util.List;

public class CreateGroupRequest {

  private String name;
  private Integer maxUsers;
  private String category;
  private String createdBy;
  private List<GroupStakeholder> stakeholders;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getMaxUsers() {
    return maxUsers;
  }

  public void setMaxUsers(Integer maxUsers) {
    this.maxUsers = maxUsers;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public List<GroupStakeholder> getStakeholders() {
    return stakeholders;
  }

  public void setStakeholders(
      List<GroupStakeholder> stakeholders) {
    this.stakeholders = stakeholders;
  }
}
