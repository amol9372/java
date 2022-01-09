package com.expensetracker.trackerapp.builder;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import java.util.List;
import java.util.stream.Collectors;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

public class UserExpenseBuilder {

  public static List<Expense> with(
      SdkIterable<Page<ExpenseTrackerBean>> expenseTrackerBeanPageIterable) {

    var lastEvaluatedKey = expenseTrackerBeanPageIterable.iterator().next().lastEvaluatedKey();
    var expenseItems = expenseTrackerBeanPageIterable.iterator().next().items();
    var expenses = expenseItems.stream().map(expenseDb -> {

      var expense = ExpenseBuilder.with(expenseDb);
      return expense;
    }).collect(Collectors.toList());

    return expenses;
  }
}
