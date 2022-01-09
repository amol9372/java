package com.expensetracker.trackerapp.serviceimpl;

import com.expensetracker.trackerapp.builder.ExpenseBuilder;
import com.expensetracker.trackerapp.builder.GroupExpenseBuilder;
import com.expensetracker.trackerapp.builder.UserExpenseBuilder;
import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import com.expensetracker.trackerapp.response.CreateExpenseResponse;
import com.expensetracker.trackerapp.response.GetExpensesResponse;
import com.expensetracker.trackerapp.response.GetExpensesResponse.GetExpensesResponseBuilder;
import com.expensetracker.trackerapp.response.NextToken;
import com.expensetracker.trackerapp.services.ExpenseService;
import com.expensetracker.trackerapp.utils.DynamoDBUtil;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final DynamoDbTable<ExpenseTrackerBean> tableMapper;
  private final DynamoDBUtil dynamoDBUtil;

  public ExpenseServiceImpl(
      DynamoDbTable<ExpenseTrackerBean> tableMapper,
      DynamoDBUtil dynamoDBUtil) {
    this.tableMapper = tableMapper;
    this.dynamoDBUtil = dynamoDBUtil;
  }

  @Override
  public CreateExpenseResponse createExpense(CreateExpenseRequest request) {

    var batchItems = ExpenseBuilder.with(request);
    var batchResult = dynamoDBUtil.batchInsert(batchItems);
    batchResult.unprocessedPutItemsForTable(tableMapper);

    var expenseId = batchItems.stream().map(item -> item.getPk()).distinct().findFirst().get();
    var response = new CreateExpenseResponse(expenseId, "Expense created Successfully");

    return response;
  }

  @Override
  public Expense getExpense(String expenseId) {
    var queryConditional = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(expenseId).build());

    var queryEnhancedRequest = QueryEnhancedRequest.builder().queryConditional(queryConditional)
        .build();
    var expenseItems = tableMapper.query(queryEnhancedRequest);

    return ExpenseBuilder.with(expenseItems);
  }

  @Override
  public List<Expense> getGroupExpenses(String groupId) {

    var queryConditional = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(groupId).build());
    var queryEnhancedRequest = QueryEnhancedRequest.builder().queryConditional(queryConditional)
        .build();

    DynamoDbIndex<ExpenseTrackerBean> groupIndex = tableMapper.index("group-index");
    var groupExpensesItems = groupIndex.query(queryEnhancedRequest);

    return GroupExpenseBuilder.with(groupExpensesItems);
  }

  @Override
  public GetExpensesResponse getUserExpenses(String userId,
      Map<String, String> startKey) {

    var queryConditional = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(userId).build());

    var exclusiveStartKey = new NextToken().createNextToken(startKey);
    var queryEnhancedRequest = QueryEnhancedRequest.builder().limit(DynamoDBUtil.PAGE_SIZE)
        .queryConditional(queryConditional).exclusiveStartKey(exclusiveStartKey)
        .build();

    DynamoDbIndex<ExpenseTrackerBean> skIndex = tableMapper.index("sk-index");
    var userExpenseDbItems = skIndex.query(queryEnhancedRequest);
    var expenseItems = UserExpenseBuilder.with(userExpenseDbItems);

    var lastEvaluatedKey = userExpenseDbItems.iterator().next().lastEvaluatedKey();
    var nextToken = new NextToken().getNextToken(lastEvaluatedKey);

    var response = new GetExpensesResponseBuilder().withExpenses(expenseItems)
        .withMessage("Fetched all Expenses Successfully").withNextToken(nextToken).build();

    return response;
  }
}
