package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Expense {
    private Integer id;
    private String description;
    private List<ExpenseUser> expenseUsers;
    private Group group;
    private Double amount;
    private User createdById;

    public Expense(Integer id, String description, List<ExpenseUser> expenseUsers, Group group, Double amount, User createdById) {
        this.id = id;
        this.description = description;
        this.expenseUsers = expenseUsers;
        this.group = group;
        this.amount = amount;
        this.createdById = createdById;
    }
}
