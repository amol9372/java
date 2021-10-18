package com.expensetracker.trackerapp.request;

import java.util.List;

public class CreateGroupRequest {

    private String name;
    private Integer maxUsers;
    private String createdBy;
    private String currencyCode;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<GroupStakeholder> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(
        List<GroupStakeholder> stakeholders) {
        this.stakeholders = stakeholders;
    }
}
