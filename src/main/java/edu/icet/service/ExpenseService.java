package edu.icet.service;

import edu.icet.dto.Expense;
import edu.icet.dto.Group;

import java.util.List;

public interface ExpenseService {
    boolean addExpense(Expense expense);

    List<Expense> getExpense();

    boolean deleteExpense(Integer id);

    boolean updateExpense(Expense expense);

    boolean settleUpExpenses(Integer groupId, Integer userId);

    boolean settleUpExpense(Integer expenseId);

    List<Expense> getExpensesByGroup(Integer groupId);
}
