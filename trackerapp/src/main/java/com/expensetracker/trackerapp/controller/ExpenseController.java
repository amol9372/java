package com.expensetracker.trackerapp.controller;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import com.expensetracker.trackerapp.response.CreateExpenseResponse;
import com.expensetracker.trackerapp.response.GetExpensesResponse;
import com.expensetracker.trackerapp.services.ExpenseService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("expense")
public class ExpenseController {

  private final ExpenseService expenseService;

  ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @PostMapping("create")
  public CreateExpenseResponse createExpense(@RequestBody CreateExpenseRequest request) {
    return expenseService.createExpense(request);
  }

  @GetMapping("{expenseId}")
  public Expense getExpense(@PathVariable String expenseId) {
    return expenseService.getExpense(expenseId);
  }

  @GetMapping("user/{userId}")
  public GetExpensesResponse getUserExpenses(@PathVariable String userId,
      @RequestParam Map<String, String> startKey) {
    return expenseService.getUserExpenses(userId, startKey);
  }

  @GetMapping("group/{groupId}")
  public List<Expense> getGroupExpenses(@PathVariable String groupId) {
    return expenseService.getGroupExpenses(groupId);
  }

  @DeleteMapping("{expenseId}")
  public void deleteExpense(@PathVariable String expenseId) {

  }


}
