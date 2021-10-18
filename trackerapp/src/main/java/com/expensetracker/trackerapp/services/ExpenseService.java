package com.expensetracker.trackerapp.services;

import com.expensetracker.trackerapp.entities.app.Expense;
import com.expensetracker.trackerapp.request.CreateExpenseRequest;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ExpenseService {

  void createExpense(CreateExpenseRequest request);

  Expense getExpense(String expenseId);

  List<Expense> getGroupExpenses(String groupId);

}
