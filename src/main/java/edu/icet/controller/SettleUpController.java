package edu.icet.controller;

import edu.icet.service.ExpenseService;
import edu.icet.service.GroupService;
import edu.icet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settleup")
@CrossOrigin
public class SettleUpController {

    final ExpenseService expenseService;
    final GroupService groupService;
    final UserService userService;

    @PostMapping("/expense/{expenseId}/user/{userId}")
    public ResponseEntity<String> settleUpExpenses(@PathVariable Integer expenseId, @PathVariable Integer userId) {
        boolean isSettled = expenseService.settleUpExpenseByUserId(expenseId, userId);

        if (isSettled) {
            return ResponseEntity.status(HttpStatus.OK).body("Expense settled successfully for user with ID: " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Expense or user not found, or an error occurred while settling.");
        }
    }
}
