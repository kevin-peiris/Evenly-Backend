package edu.icet.service;

import edu.icet.dto.Expense;
import edu.icet.dto.ExpenseUser;

import java.util.List;

public interface ExpenseUserService {
    boolean addExpenseUser(ExpenseUser expenseUser);

    List<ExpenseUser> getExpenseUser();

    boolean deleteExpenseUser(Integer id);

    boolean updateExpenseUser(ExpenseUser expenseUser);
}
