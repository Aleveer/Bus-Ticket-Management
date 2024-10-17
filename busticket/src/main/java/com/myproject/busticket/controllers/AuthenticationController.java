package com.myproject.busticket.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myproject.busticket.exceptions.ModelNotFoundException;
import com.myproject.busticket.exceptions.TimeOutException;
import com.myproject.busticket.exceptions.AccountStatusException;
import com.myproject.busticket.exceptions.ValidationException;
import com.myproject.busticket.models.LoginUserModel;
import com.myproject.busticket.models.RegisterUserModel;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.VerifyAccountModel;
import com.myproject.busticket.services.AuthenticationService;
import com.myproject.busticket.services.JwtService;
import com.myproject.busticket.services.AccountService;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/verify")
    public String showVerificationPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "verify";
    }

    @GetMapping("/forgot-password")
    public String requestPasswordResetPage() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @ResponseBody
    @PostMapping("/signup")
    public Map<String, Object> signUp(@RequestBody RegisterUserModel registerUserDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Account registerAccount = authenticationService.signUp(registerUserDto);
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("data", registerAccount);
            return response;
        } catch (ValidationException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> signIn(@RequestBody LoginUserModel loginUserDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Account authenticatedAccount = authenticationService.signIn(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedAccount);
            authenticatedAccount.setLoginToken(jwtToken);
            accountService.save(authenticatedAccount);

            // LoginResponse loginResponse = new LoginResponse(jwtToken,
            // jwtService.getExpirationTime());
            response.put("success", true);
            response.put("message", "Login successful");
            // response.put("token", jwtToken);
            return response;
        } catch (AccountStatusException e) {
            response.put("success", false);
            response.put("message", "Authentication failed: " + e.getMessage());
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return response;
        }
    }

    @PostMapping("/logout")
    public Map<String, Object> signOut(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = request.get("email");
            authenticationService.signOut(email);
            response.put("success", true);
            response.put("message", "User signed out successfully");
        } catch (ModelNotFoundException e) {
            throw e;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred during signing out");
        }

        return response;
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyAccount(@RequestBody VerifyAccountModel verifyAccountModel) {
        Map<String, Object> response = new HashMap<>();
        try {
            authenticationService.verifyAccount(verifyAccountModel);
            response.put("success", true);
            response.put("message", "Account verified successfully");
            return ResponseEntity.ok(response);
        } catch (ModelNotFoundException | ValidationException | TimeOutException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ResponseBody
    @PostMapping("/forgot-password")
    public Map<String, Object> requestPasswordReset(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = request.get("email");
            authenticationService.requestPasswordReset(email);
            response.put("success", true);
            response.put("message", "Password reset link has been sent to your email");
            return response;
        } catch (ModelNotFoundException | AccountStatusException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        }
    }

    @ResponseBody
    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = request.get("token");
            String password = request.get("password");
            authenticationService.resetPassword(token, password);
            response.put("success", true);
            response.put("message", "Password reset successfully");
            return response;
        } catch (ModelNotFoundException | ValidationException | AccountStatusException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        }
    }

    @ResponseBody
    @PostMapping("/resend")
    public ResponseEntity<Map<String, Object>> resendVerificationEmail(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = request.get("email");
            authenticationService.resendVerificationCode(email);
            response.put("success", true);
            response.put("message", "Verification email has been resent");
            return ResponseEntity.ok(response);
        } catch (ModelNotFoundException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}