package com.expensetracker.trackerapp.serviceimpl;

import com.expensetracker.trackerapp.builder.ExpenseBuilder;
import com.expensetracker.trackerapp.builder.GroupExpenseBuilder;
import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import com.expensetracker.trackerapp.entities.db.ItemType;
import com.expensetracker.trackerapp.services.ExpenseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  DynamoDbTable<ExpenseTrackerBean> tableMapper;

  @Autowired
  DynamoDbEnhancedClient dynamoDbEnhancedClient;

  @Autowired
  DynamoDbTable<ExpenseTrackerBean> groupIndexMapper;

  @Override
  public void createExpense(CreateExpenseRequest request) {

    var expenseBatch = ExpenseBuilder.with(request);
    Builder<ExpenseTrackerBean> writeBatch = WriteBatch.builder(ExpenseTrackerBean.class);

    expenseBatch.stream().forEach(expense -> {
      writeBatch.addPutItem(expense);
    });

    BatchWriteItemEnhancedRequest enhancedRequest = BatchWriteItemEnhancedRequest.builder()
        .addWriteBatch(writeBatch.mappedTableResource(tableMapper).build()).build();

    BatchWriteResult batchResult = dynamoDbEnhancedClient.batchWriteItem(enhancedRequest);
    System.out.println(batchResult.toString());
  }

  @Override
  public Expense getExpense(String expenseId) {
    var queryConditional = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(expenseId).build());

    var queryEnhancedRequest = QueryEnhancedRequest.builder().queryConditional(queryConditional)
        .build();
    var expenseItems = tableMapper.query(queryEnhancedRequest);
    var expense = ExpenseBuilder.with(expenseItems);

    return expense;
  }

  @Override
  public List<Expense> getGroupExpenses(String groupId) {

    var queryConditional = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(groupId).build());
    var queryEnhancedRequest = QueryEnhancedRequest.builder().queryConditional(queryConditional)
        .build();

    DynamoDbIndex<ExpenseTrackerBean> groupIndex = tableMapper.index("group-index");
    var groupExpensesItems = groupIndex.query(queryEnhancedRequest);

    var groupExpenses = GroupExpenseBuilder.with(groupExpensesItems);

    return groupExpenses;
  }
}
