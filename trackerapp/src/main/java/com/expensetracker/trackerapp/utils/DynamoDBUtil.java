package com.expensetracker.trackerapp.utils;

import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import java.util.List;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;

@Component
public class DynamoDBUtil {

  private final DynamoDbTable<ExpenseTrackerBean> tableMapper;

  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  DynamoDBUtil(DynamoDbTable<ExpenseTrackerBean> tableMapper,
      DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    this.tableMapper = tableMapper;
    this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
  }

  public BatchWriteResult batchInsert(List<ExpenseTrackerBean> items) {
    Builder<ExpenseTrackerBean> writeBatch = WriteBatch.builder(ExpenseTrackerBean.class);

    items.forEach(writeBatch::addPutItem);

    BatchWriteItemEnhancedRequest enhancedRequest = BatchWriteItemEnhancedRequest.builder()
        .addWriteBatch(writeBatch.mappedTableResource(tableMapper).build()).build();

    return dynamoDbEnhancedClient.batchWriteItem(enhancedRequest);
  }

}
