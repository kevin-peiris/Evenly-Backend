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
public class SettleUpController {

    final ExpenseService expenseService;
    final GroupService groupService;
    final UserService userService;

    @PostMapping("/group/{groupId}/user/{userId}")
    public ResponseEntity<String> settleUpExpenses(@PathVariable Integer groupId, @PathVariable Integer userId) {
        expenseService.settleUpExpenses(groupId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expenses settled successfully for user in group");
    }


}
