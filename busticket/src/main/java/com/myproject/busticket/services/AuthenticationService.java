package com.myproject.busticket.services;

import jakarta.mail.MessagingException;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.User;
import com.myproject.busticket.models.VerifyUserModel;
import com.myproject.busticket.models.LoginUserModel;
import com.myproject.busticket.models.RegisterUserModel;
import com.myproject.busticket.models.Role;
import com.myproject.busticket.enums.UserStatus;
import com.myproject.busticket.repositories.RoleRepository;
import com.myproject.busticket.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    @Autowired
    private RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User signup(RegisterUserModel input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        // TODO: Prompt user to update their profile after registration: full name,
        // TODO: phone number
        user.setFullName("Nguyen Van A");
        user.setPhone("01234567890");
        Role role = roleRepository.findByName("customer").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        user.setStatus(UserStatus.unverified);
        user.setVerificationCode(generateVerificationCode());
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);
        user.setVerificationExpiration(expirationTime);
        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    // TODO: Implement the possible test cases for the following method:
    public User signin(LoginUserModel input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getStatus() == UserStatus.unverified) {
            throw new RuntimeException("Account not verified. Please verify your account.");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect email or password");
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
        return user;
    }

    public void signout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getLoginToken() == null) {
            throw new RuntimeException("User is not logged in");
        }

        user.setLoginToken(null);
        userRepository.save(user);
    }

    // Implement forgot password feature
    // Step 1: User requests a password reset
    // Step 2: Generate a password reset token and send it to the user's email
    // Step 3: User clicks on the link in the email and is redirected to a page to
    // reset the password
    // Step 4: User enters a new password and the password is updated in the
    // database
    // Step 5: User is redirected to the login page to log in with the new password
    // Step 6: User logs in successfully
    // Step 7: Password reset token is invalidated
    // Step 8: User can no longer use the password reset link
    // Step 9: User can request a new password reset if needed
    // Step 10: User receives a new password reset token and the process repeats
    // Step 11: Password reset token expires after a certain period of time
    // Step 12: User must use the password reset token within the expiration time
    // Step 13: User receives an error message if the password reset token is
    // expired
    // Step 14: User can request a new password reset if the token is expired
    // Step 15: User receives a new password reset token and the process repeats
    // Step 16: User can only reset the password for their own account
    // Step 17: User receives an error message if they try to reset the password for
    // another account
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        if (user.getStatus() == UserStatus.unverified) {
            throw new RuntimeException("User account is not verified");
        }

        if (user.getStatus() == UserStatus.banned) {
            throw new RuntimeException("User account is banned");
        }

        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        user.setPasswordResetExpiration(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        sendPasswordResetEmail(user);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid password reset token"));

        if (user.getPasswordResetExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token has expired");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        if (user.getStatus() != UserStatus.verified) {
            throw new RuntimeException("User account is not verified");
        }

        if (!isValidPassword(newPassword)) {
            throw new RuntimeException("New password does not meet security requirements");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiration(null);
        userRepository.save(user);
    }

    // TODO: Implement this in validation folder
    private boolean isValidPassword(String password) {
        return password.length() >= 8; // Example: minimum 8 characters
    }

    public void verifyUser(VerifyUserModel input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setStatus(UserStatus.verified);
                user.setVerificationCode(null);
                user.setVerificationExpiration(null);
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiration(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }

    // TODO: Update URL here if needed
    private void sendPasswordResetEmail(User user) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost/reset-password?token=" + user.getPasswordResetToken();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Password Reset Request</h2>"
                + "<p style=\"font-size: 16px;\">Click the link below to reset your password:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<a href=\"" + resetLink
                + "\" style=\"font-size: 18px; font-weight: bold; color: #007bff;\">Reset Password</a>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}