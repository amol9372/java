package com.expensetracker.trackerapp.services;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import com.expensetracker.trackerapp.response.CreateExpenseResponse;
import com.expensetracker.trackerapp.response.GetExpensesResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface ExpenseService {

  CreateExpenseResponse createExpense(CreateExpenseRequest request);

  Expense getExpense(String expenseId);

  List<Expense> getGroupExpenses(String groupId);

  GetExpensesResponse getUserExpenses(String userId,
      Map<String, String> startKey);

}
