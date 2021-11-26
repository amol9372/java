package com.expensetracker.userservice.config;

import com.expensetracker.userservice.entities.db.ExpenseTrackerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DatabaseConfig {

  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
    DynamoDbClient ddb = DynamoDbClient.builder()
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .region(Region.AP_SOUTH_1)
        .build();

    return DynamoDbEnhancedClient.builder().dynamoDbClient(ddb)
        .build();
  }

  @Bean
  public DynamoDbTable<ExpenseTrackerBean> tableMapper() {
    return dynamoDbEnhancedClient().table("expense_tracker",
        TableSchema.fromBean(ExpenseTrackerBean.class));
  }

}
