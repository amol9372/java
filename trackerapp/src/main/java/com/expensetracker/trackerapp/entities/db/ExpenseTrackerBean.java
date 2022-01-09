package com.expensetracker.trackerapp.entities.db;

import java.time.LocalDateTime;
import java.util.StringJoiner;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class ExpenseTrackerBean {

  /**
   * Primary Key
   */
  private String pk;
  private String sk;

  /**
   * User
   */
  private String name;
  private String email;
  private String currency;

  /**
   * Expense
   */
  private String expenseName;
  private Double cost;
  private String paidBy;
  private Double owes;

  /**
   * Group
   */
  private String group;
  private String groupName;
  private Integer maxUsers;

  /**
   * Date-Time, Created-by
   */
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private String createdBy;

  /**
   * Misc
   */
  private String category;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @DynamoDbPartitionKey
  public String getPk() {
    return pk;
  }

  public void setPk(String pk) {
    this.pk = pk;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "sk-index")
  @DynamoDbSortKey
  public String getSk() {
    return sk;
  }

  public void setSk(String sk) {
    this.sk = sk;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @DynamoDbAttribute(value = "expense_name")
  public String getExpenseName() {
    return expenseName;
  }

  public void setExpenseName(String expenseName) {
    this.expenseName = expenseName;
  }

  @DynamoDbAttribute(value = "paid_by")
  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(String paidBy) {
    this.paidBy = paidBy;
  }

  public Double getOwes() {
    return owes;
  }

  public void setOwes(Double owes) {
    this.owes = owes;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "group-index")
  @DynamoDbAttribute(value = "group")
  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  @DynamoDbAttribute(value = "max_users")
  public Integer getMaxUsers() {
    return maxUsers;
  }

  public void setMaxUsers(Integer maxUsers) {
    this.maxUsers = maxUsers;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @DynamoDbAttribute(value = "created_date")
  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  @DynamoDbAttribute(value = "updated_date")
  public LocalDateTime getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(LocalDateTime updatedDate) {
    this.updatedDate = updatedDate;
  }

  @DynamoDbAttribute(value = "created_by")
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  @DynamoDbAttribute(value = "category")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @DynamoDbAttribute(value = "group_name")
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ExpenseTrackerBean.class.getSimpleName() + "[", "]")
        .add("pk='" + pk + "'")
        .add("sk='" + sk + "'")
        .add("name='" + name + "'")
        .add("email='" + email + "'")
        .add("expenseName='" + expenseName + "'")
        .add("expenseCategory='" + category + "'")
        .add("cost=" + cost)
        .add("paidBy='" + paidBy + "'")
        .add("owes=" + owes)
        .add("group='" + group + "'")
        .add("maxUsers=" + maxUsers)
        .add("currency='" + currency + "'")
        .add("createdDate=" + createdDate)
        .add("updatedDate=" + updatedDate)
        .add("createdBy='" + createdBy + "'")
        .toString();
  }

  public static class ExpenseTrackerBeanBuilder {

    private ExpenseTrackerBean expenseTrackerBean = new ExpenseTrackerBean();

    public ExpenseTrackerBeanBuilder withPk(String pk) {
      expenseTrackerBean.setPk(pk);
      return this;
    }

    public ExpenseTrackerBeanBuilder withSk(String sk) {
      expenseTrackerBean.setSk(sk);
      return this;
    }

    public ExpenseTrackerBeanBuilder withEmail(String email) {
      expenseTrackerBean.setEmail(email);
      return this;
    }

    public ExpenseTrackerBeanBuilder withName(String name) {
      expenseTrackerBean.setName(name);
      return this;
    }

    public ExpenseTrackerBeanBuilder withExpenseName(String expenseName) {
      expenseTrackerBean.setExpenseName(expenseName);
      return this;
    }

    public ExpenseTrackerBeanBuilder withCreatedBy(String createdBy) {
      expenseTrackerBean.setCreatedBy(createdBy);
      return this;
    }

    public ExpenseTrackerBeanBuilder withGroup(String groupName) {
      expenseTrackerBean.setGroup(groupName);
      return this;
    }

    public ExpenseTrackerBeanBuilder withCost(Double cost) {
      expenseTrackerBean.setCost(cost);
      return this;
    }

    public ExpenseTrackerBeanBuilder withCategory(String category) {
      expenseTrackerBean.setCategory(category);
      return this;
    }

    public ExpenseTrackerBeanBuilder withPaidBy(String paidBy) {
      expenseTrackerBean.setPaidBy(paidBy);
      return this;
    }

    public ExpenseTrackerBeanBuilder withOwes(Double owes) {
      expenseTrackerBean.setOwes(owes);
      return this;
    }

    public ExpenseTrackerBeanBuilder withMaxUsers(Integer maxUsers) {
      expenseTrackerBean.setMaxUsers(maxUsers);
      return this;
    }

    public ExpenseTrackerBeanBuilder withCreatedDate(LocalDateTime createdDate) {
      expenseTrackerBean.setCreatedDate(createdDate);
      return this;
    }

    public ExpenseTrackerBean build() {
      return expenseTrackerBean;
    }

  }
}
