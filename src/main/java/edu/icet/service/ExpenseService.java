package edu.icet.service;

import edu.icet.dto.Expense;
import edu.icet.dto.ExpenseResponse;

import java.util.List;

public interface ExpenseService {
    boolean addExpense(Expense expense);

    List<Expense> getExpense();

    boolean deleteExpense(Integer id);

    boolean updateExpense(Expense expense);

    boolean settleUpExpenseByUserId(Integer expenseId, Integer userId);

    boolean settleUpExpense(Integer expenseId);

    List<ExpenseResponse> getExpensesByGroup(Integer groupId);
}
