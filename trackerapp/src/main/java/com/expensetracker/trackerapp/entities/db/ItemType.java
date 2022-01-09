package com.expensetracker.trackerapp.entities.db;

public enum ItemType {

  USER("user_"),
  EXPENSE("expense_"),
  GROUP("group_"),
  CATEGORY("category_");

  private final String value;

  ItemType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}