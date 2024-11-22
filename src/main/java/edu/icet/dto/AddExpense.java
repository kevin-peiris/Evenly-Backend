package edu.icet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Data
public class AddExpense {
    private Integer createdById;
    private String description;
    private LocalDate date;
    private Double amount;
    private Integer groupId;
    private Set<User> amountPaidBy;
    private Set<User> amountOwedBy;
    private String splitType;
    private Map<Integer, Double> customAmounts;
    private Map<Integer, Double> percentages;
}

