package com.expensetracker.userservice.entities.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class UserPreferences {

  private Double monthlyLimit;
  private String language;
  private String currency;

  public static class UserPreferencesBuilder {

    private final UserPreferences userPreferences = new UserPreferences();

    public UserPreferencesBuilder withMonthlyLimit(Double limit){
      userPreferences.setMonthlyLimit(limit);
      return this;
    }

    public UserPreferencesBuilder withLanguage(String language){
      userPreferences.setLanguage(language);
      return this;
    }

    public UserPreferencesBuilder withCurrency(String currency){
      userPreferences.setCurrency(currency);
      return this;
    }

    public UserPreferences build(){
      return userPreferences;
    }

  }

  public Double getMonthlyLimit() {
    return monthlyLimit;
  }

  public void setMonthlyLimit(Double monthlyLimit) {
    this.monthlyLimit = monthlyLimit;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
