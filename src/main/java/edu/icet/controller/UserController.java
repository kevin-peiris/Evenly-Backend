package edu.icet.controller;

import edu.icet.dto.User;
import edu.icet.service.OtpService;
import edu.icet.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl service;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (service.registerUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. OTP sent to email.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to register user data.");
        }
    }

    @GetMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestParam("email") String email,
            @RequestParam("otp") int otp) {

        boolean isOtpValid = otpService.verifyOtp(email, otp);
        if (isOtpValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP.");
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam("email") String email) {

        User user = service.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }

        int otp = otpService.generateOtp(email);
        otpService.sendOtpEmail(email, otp);
        return ResponseEntity.ok("OTP sent to email.");
    }



    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("otp") int otp,
            @RequestParam("newPassword") String newPassword) {

        if (!otpService.verifyOtp(email, otp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP.");
        }

        boolean isUpdated = service.updateUserPassword(email, newPassword);
        if (isUpdated) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to reset password.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (service.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to delete user data.");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = service.getUser();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = service.findByEmail(email);
        if (user==null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody User user) {
        if (service.updateUser(user)) {
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to update user data.");
        }
    }
}
