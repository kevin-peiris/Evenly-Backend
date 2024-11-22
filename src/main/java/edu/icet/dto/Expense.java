package edu.icet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Expense {
    private Integer id;
    private String description;
    private LocalDateTime date;
    private List<ExpenseUser> expenseUsers;
    private Group group;
    private Double amount;
    private User createdById;

    public Expense(Integer id, String description, LocalDateTime date, List<ExpenseUser> expenseUsers, Group group, Double amount, User createdById) {
        this.id = id;
        this.description = description;
        this.date=date;
        this.expenseUsers = expenseUsers;
        this.group = group;
        this.amount = amount;
        this.createdById = createdById;
    }
}
