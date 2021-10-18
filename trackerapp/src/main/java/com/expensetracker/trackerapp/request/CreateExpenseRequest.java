package com.expensetracker.trackerapp.request;

import com.expensetracker.trackerapp.entities.app.ExpenseStakeholders;
import java.time.LocalDateTime;

public class CreateExpenseRequest {

  private Double cost;
  private String name;
  private String category;
  private String groupId;
  private String paidBy;
  private String createdBy;
  private ExpenseStakeholders stakeholders;

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(String paidBy) {
    this.paidBy = paidBy;
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
}
