package edu.icet.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseResponse {
    private Integer id;
    private String description;
    private Double amount;
    private Integer groupId;
    private Integer createdById;
    private List<ExpenseUser> expenseUsers;
}
