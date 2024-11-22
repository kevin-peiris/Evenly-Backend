package edu.icet.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ExpenseResponse {
    private Integer id;
    private String description;
    private LocalDateTime date;
    private Double amount;
    private Integer groupId;
    private Integer createdById;
    private List<ExpenseUser> expenseUsers;
}
