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
import com.myproject.busticket.exceptions.ValidationException;
import com.myproject.busticket.models.LoginUserModel;
import com.myproject.busticket.models.RegisterUserModel;
import com.myproject.busticket.models.User;
import com.myproject.busticket.models.VerifyUserModel;
import com.myproject.busticket.responses.LoginResponse;
import com.myproject.busticket.services.AuthenticationService;
import com.myproject.busticket.services.JwtService;
import com.myproject.busticket.services.UserService;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/request-password-reset")
    public String requestPasswordResetPage() {
        return "request-password-reset";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, @RequestParam String email) {
        return "reset-password";
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUserDto) {
        // TODO: Check for existing user by email before registering:
        try {
            if (userService.getUserByEmail(registerUserDto.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(new User());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> authenticate(@RequestBody LoginUserModel loginUserDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            User authenticatedUser = authenticationService.signin(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            authenticatedUser.setLoginToken(jwtToken);
            userService.save(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            response.put("response", loginResponse);
            response.put("message", "Login successful");
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Authentication failed");
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
            response.put("message", "An error occurred during sign out");
        }

        return response;
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody VerifyUserModel verifyUserDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            authenticationService.verifyUser(verifyUserDto);
            response.put("success", true);
            response.put("message", "Account verified successfully");
            return ResponseEntity.ok(response);
        } catch (ModelNotFoundException | ValidationException | TimeOutException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authenticationService.requestPasswordReset(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        authenticationService.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}