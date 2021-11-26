package com.expensetracker.trackerapp.serviceimpl;

import com.expensetracker.trackerapp.builder.GroupBuilder;
import com.expensetracker.trackerapp.entities.app.Group;
import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import com.expensetracker.trackerapp.request.CreateGroupRequest;
import com.expensetracker.trackerapp.services.GroupService;
import com.expensetracker.trackerapp.utils.DynamoDBUtil;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class GroupServiceImpl implements GroupService {

  private final DynamoDbTable<ExpenseTrackerBean> tableMapper;
  private final DynamoDBUtil dynamoDBUtil;

  public GroupServiceImpl(
      DynamoDbTable<ExpenseTrackerBean> tableMapper,
      DynamoDBUtil dynamoDBUtil) {
    this.tableMapper = tableMapper;
    this.dynamoDBUtil = dynamoDBUtil;
  }

  @Override
  public void createGroup(CreateGroupRequest request) {

    var batchItems = GroupBuilder.with(request);
    var batchResult = dynamoDBUtil.batchInsert(batchItems);
    var unprocessedItems = batchResult.unprocessedPutItemsForTable(tableMapper);
  }

  @Override
  public Group getGroupDetail(String groupId) {
    return null;
  }
}
