package com.expensetracker.trackerapp.builder;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import com.expensetracker.trackerapp.entities.db.ItemType;
import java.util.List;
import java.util.stream.Collectors;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

public class GroupExpenseBuilder {

  public static List<Expense> with(
      SdkIterable<Page<ExpenseTrackerBean>> expenseTrackerBeanPageIterable) {

    var expenseItems = expenseTrackerBeanPageIterable.iterator().next().items();
    var expenseByGroup = expenseItems.stream()
        .collect(Collectors.groupingBy(ExpenseTrackerBean::getPk));

    var expenses = expenseByGroup.values().stream().map(expenseTrackerBeans -> {

      var partitions = expenseTrackerBeans.stream()
          .collect(Collectors.partitioningBy(e -> e.getSk().contains(ItemType.USER.getValue())));

      var expense = ExpenseBuilder.with(partitions.get(Boolean.FALSE).get(0));
      var stakeholders = ExpenseBuilder.with(partitions.get(Boolean.TRUE));

      stakeholders.setPaidBy(expense.getPaidBy());
      expense.setStakeholders(stakeholders);

      return expense;
    }).collect(Collectors.toList());

    return expenses;
  }


}
