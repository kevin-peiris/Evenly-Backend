package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.icet.util.ExpenseUserType;
import lombok.Data;

@Data
public class ExpenseUser {
    private Integer id;
    private User user;
    private Double amount;
    private Expense expense;
    private ExpenseUserType expenseUserType;
}
