package edu.icet.controller;

import edu.icet.dto.*;
import edu.icet.service.ExpenseService;
import edu.icet.service.GroupService;
import edu.icet.service.UserService;
import edu.icet.util.ExpenseUserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ExpenseController {

    final ExpenseService expenseService;
    final GroupService groupService;
    final UserService userService;

    @PostMapping("/expense")
    public ResponseEntity<String> add(@RequestBody AddExpense addExpense) {

        List<ExpenseUser> expenseUsers = convertToExpenseUsers(addExpense);

        Expense expense = new Expense(
                null,
                addExpense.getDescription(),
                LocalDateTime.now().now(),
                expenseUsers,
                groupService.findById(addExpense.getGroupId()),
                addExpense.getAmount(),
                userService.findById(addExpense.getCreatedById())
        );

        if (expenseService.addExpense(expense)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to add Expense data.");
        }
    }

    private List<ExpenseUser> convertToExpenseUsers(AddExpense addExpense) {
        List<ExpenseUser> expenseUsers = new ArrayList<>();
        Double totalAmount = addExpense.getAmount();
        int numOwedUsers = addExpense.getAmountOwedBy().size();

        for (User user : addExpense.getAmountPaidBy()) {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setUser(user);
            expenseUser.setAmount(totalAmount);  // Amount paid by this user
            expenseUser.setExpenseUserType(ExpenseUserType.PAID);
            expenseUsers.add(expenseUser);
        }

        switch (addExpense.getSplitType().toUpperCase()) {
            case "EQUAL":
                double equalAmount = totalAmount / numOwedUsers;
                for (User user : addExpense.getAmountOwedBy()) {
                    ExpenseUser expenseUser = new ExpenseUser();
                    expenseUser.setUser(user);
                    expenseUser.setAmount(equalAmount);
                    expenseUser.setExpenseUserType(ExpenseUserType.OWED);
                    expenseUsers.add(expenseUser);
                }
                break;

            case "PERCENTAGE":
                for (User user : addExpense.getAmountOwedBy()) {
                    ExpenseUser expenseUser = new ExpenseUser();
                    Double percentage = addExpense.getPercentages().get(user.getId());
                    if (percentage != null) {
                        expenseUser.setAmount(totalAmount * (percentage / 100));
                    }
                    expenseUser.setUser(user);
                    expenseUser.setExpenseUserType(ExpenseUserType.OWED);
                    expenseUsers.add(expenseUser);
                }
                break;

            case "CUSTOM":
                for (User user : addExpense.getAmountOwedBy()) {
                    ExpenseUser expenseUser = new ExpenseUser();
                    Double customAmount = addExpense.getCustomAmounts().get(user.getId());
                    if (customAmount != null) {
                        expenseUser.setAmount(customAmount);
                    }
                    expenseUser.setUser(user);
                    expenseUser.setExpenseUserType(ExpenseUserType.OWED);
                    expenseUsers.add(expenseUser);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid split type provided: " + addExpense.getSplitType());
        }

        return expenseUsers;
    }


    @DeleteMapping("/expense/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (expenseService.deleteExpense(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Expense deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to delete Expense data.");
        }
    }

    @GetMapping("/expense/group/{groupId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroup(@PathVariable Integer groupId) {
        List<ExpenseResponse> expenses = expenseService.getExpensesByGroup(groupId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> get() {
        List<Expense> expenses=expenseService.getExpense();
        if (expenses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/expense")
    public ResponseEntity<String> update(@RequestBody Expense expense){
        if (expenseService.updateExpense(expense)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Expense updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to update Expense data.");
        }
    }
}
