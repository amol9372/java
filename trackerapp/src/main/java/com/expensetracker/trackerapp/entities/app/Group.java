package com.expensetracker.trackerapp.entities.app;

import java.time.LocalDateTime;
import java.util.List;

public class Group {

  private String name;
  private String groupId;
  private String category;
  private String maxUsers;
  private String createdBy;
  private List<GroupMembers> members;
  private LocalDateTime createdDate;
  private String logoUrl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getMaxUsers() {
    return maxUsers;
  }

  public void setMaxUsers(String maxUsers) {
    this.maxUsers = maxUsers;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public List<GroupMembers> getMembers() {
    return members;
  }

  public void setMembers(List<GroupMembers> members) {
    this.members = members;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }
}
