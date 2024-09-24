package com.myproject.busticket.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myproject.busticket.models.LoginUserModel;
import com.myproject.busticket.models.RegisterUserModel;
import com.myproject.busticket.models.User;
import com.myproject.busticket.models.VerifyUserModel;
import com.myproject.busticket.responses.LoginResponse;
import com.myproject.busticket.services.AuthenticationService;
import com.myproject.busticket.services.JwtService;
import com.myproject.busticket.services.UserService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUserDto) {
        User authenticatedUser = authenticationService.signin(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        authenticatedUser.setLoginToken(jwtToken);
        userService.save(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> signOut(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authenticationService.signOut(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserModel verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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