package edu.icet.dto;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class AddExpense {
    private Integer createdById;
    private String description;
    private Double amount;
    private Integer groupId;
    private Set<User> amountPaidBy;
    private Set<User> amountOwedBy;
    private String splitType;
    private Map<Integer, Double> customAmounts;
    private Map<Integer, Double> percentages;
}

