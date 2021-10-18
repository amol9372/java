package com.expensetracker.trackerapp.entities.app;

import java.time.LocalDateTime;

public class Expense {

  private String expenseId;
  private String name;
  private Double cost;
  private String groupName;
  private String groupId;
  private String category;
  private Object comments;
  private String createdBy;
  private LocalDateTime createdDate;
  private String paidBy;
  private ExpenseStakeholders stakeholders;

  public String getExpenseId() {
    return expenseId;
  }

  public void setExpenseId(String expenseId) {
    this.expenseId = expenseId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Object getComments() {
    return comments;
  }

  public void setComments(Object comments) {
    this.comments = comments;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public ExpenseStakeholders getStakeholders() {
    return stakeholders;
  }

  public void setStakeholders(ExpenseStakeholders stakeholders) {
    this.stakeholders = stakeholders;
  }

  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(String paidBy) {
    this.paidBy = paidBy;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
