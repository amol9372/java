package com.expensetracker.trackerapp.controller;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import com.expensetracker.trackerapp.services.ExpenseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("expense")
public class ExpenseController {

  @Autowired
  ExpenseService expenseService;

  @PostMapping("create")
  public void createExpense(@RequestBody CreateExpenseRequest request) {
    expenseService.createExpense(request);
  }

  @GetMapping("{expenseId}")
  public Expense getExpense(@PathVariable String expenseId) {
    return expenseService.getExpense(expenseId);
  }

  @GetMapping("group/{groupId}")
  public List<Expense> getGroupExpenses(@PathVariable String groupId) {
    return expenseService.getGroupExpenses(groupId);
  }

  @DeleteMapping("{expenseId}")
  public void deleteExpense(@PathVariable String expenseId) {

  }


}
