package com.expensetracker.trackerapp.builder;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.entities.app.ExpenseStakeholders;
import com.expensetracker.trackerapp.entities.app.Users;
import com.expensetracker.trackerapp.entities.db.ExpenseTrackerBean;
import com.expensetracker.trackerapp.entities.db.ItemType;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

public class ExpenseBuilder {

  public static Expense with(PageIterable<ExpenseTrackerBean> expenseTrackerBeanPageIterable) {

    var partitions = expenseTrackerBeanPageIterable.items().stream()
        .collect(Collectors.partitioningBy(e -> e.getSk().contains(ItemType.USER.getValue())));

    var expense = with(partitions.get(Boolean.FALSE).get(0));
    var stakeholders = with(partitions.get(Boolean.TRUE));

    stakeholders.setPaidBy(expense.getPaidBy());
    expense.setStakeholders(stakeholders);

    return expense;
  }

  public static Expense with(ExpenseTrackerBean expenseDetails) {
    var expense = new Expense();
    expense.setExpenseId(expenseDetails.getPk());
    var expenseName = expenseDetails.getExpenseName() == null ? expenseDetails.getSk()
        : expenseDetails.getExpenseName();
    expense.setName(expenseName);
    expense.setGroupId(expenseDetails.getGroup());
    expense.setGroupName(expenseDetails.getGroupName());
    expense.setCost(expenseDetails.getCost());
    expense.setCategory(expenseDetails.getCategory());
    expense.setCreatedBy(expenseDetails.getCreatedBy());
    expense.setCreatedDate(expenseDetails.getCreatedDate());
    expense.setPaidBy(expenseDetails.getPaidBy());
    expense.setComments(null);   // TODO
    return expense;
  }

  public static ExpenseStakeholders with(List<ExpenseTrackerBean> expenses) {

    var stakeHolders = new ExpenseStakeholders();

    var users = expenses.stream().map(expense -> {
      var user = new Users();
      user.setUserId(expense.getSk());
      user.setEmail(expense.getEmail());
      user.setOwes(expense.getOwes());
      return user;
    }).collect(Collectors.toList());

    stakeHolders.setUsers(users);
    return stakeHolders;
  }

  public static List<ExpenseTrackerBean> with(CreateExpenseRequest request) {

    var expenseId = ItemType.EXPENSE.getValue().concat(UUID.randomUUID().toString());

    var expenseDetails = new ExpenseTrackerBean.ExpenseTrackerBeanBuilder().withPk(expenseId)
        .withSk(request.getName())
        .withCost(request.getCost()).withCreatedBy(request.getCreatedBy())
        .withPaidBy(request.getPaidBy())
        .withCreatedDate(LocalDateTime.now())
        .withCategory(request.getCategory()).withGroup(request.getGroupId()).build();

    var expenseStakeholders = request.getStakeholders().getUsers().stream().map(s -> {

      var userExpense = new ExpenseTrackerBean.ExpenseTrackerBeanBuilder().withPk(expenseId)
          .withSk(s.getUserId())
          .withExpenseName(request.getName())
          .withCost(request.getCost()).withCreatedBy(request.getCreatedBy())
          .withPaidBy(request.getPaidBy()).withOwes(s.getOwes())
          .withCreatedDate(LocalDateTime.now())
          .withCategory(request.getCategory()).withGroup(request.getGroupId()).build();

      return userExpense;

    }).collect(Collectors.toList());

    expenseStakeholders.add(expenseDetails);
    return expenseStakeholders;

  }

}
