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
import com.myproject.busticket.exceptions.ModelNotFoundException;
import com.myproject.busticket.exceptions.TimeOutException;
import com.myproject.busticket.exceptions.UserStatusException;
import com.myproject.busticket.exceptions.ValidationException;
import com.myproject.busticket.repositories.RoleRepository;
import com.myproject.busticket.repositories.UserRepository;
import com.myproject.busticket.validations.UserValidation;

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

    @Autowired
    private UserService userService;

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

    public User signUp(RegisterUserModel input) {
        User user = new User();
        if (!UserValidation.isValidEmail(input.getEmail())) {
            throw new ValidationException("Invalid email");
        }

        if (UserValidation.isValidEmail(input.getEmail())) {
            if (userService.getUserByEmail(input.getEmail()).isPresent()) {
                throw new ValidationException("Email already exists");
            }
            user.setEmail(input.getEmail());
        } else {
            throw new ValidationException("Invalid email");
        }

        if (UserValidation.isValidPassword(input.getPassword())) {
            user.setPassword(passwordEncoder.encode(input.getPassword()));
        } else {
            throw new ValidationException(
                    "Invalid password. Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 special character, 1 number.");
        }
        user.setPassword(passwordEncoder.encode(input.getPassword()));
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

    public User signIn(LoginUserModel input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserStatusException("User not found"));
        if (user.getStatus() == UserStatus.unverified) {
            throw new UserStatusException("Account not verified. Please verify your account.");
        }
        if (user.getStatus() == UserStatus.banned) {
            throw new UserStatusException("Account is banned. Please contact customer support.");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ValidationException("Incorrect email or password");
        } catch (Exception e) {
            throw new ValidationException("Authentication failed");
        }
        return user;
    }

    public void signOut(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ModelNotFoundException("User not found"));

        if (user.getLoginToken() == null) {
            throw new ValidationException("User is not logged in");
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
                .orElseThrow(() -> new UserStatusException("User not found. Please check your input."));

        if (!user.isEnabled()) {
            throw new UserStatusException("User account is disabled.");
        }

        if (user.getStatus() == UserStatus.unverified) {
            throw new UserStatusException("User account is not verified.");
        }

        if (user.getStatus() == UserStatus.banned) {
            throw new UserStatusException("User account is banned. Please contact customer support.");
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
            throw new UserStatusException("Password reset token has expired");
        }

        if (!user.isEnabled()) {
            throw new UserStatusException("User account is disabled");
        }

        if (user.getStatus() != UserStatus.verified) {
            throw new UserStatusException("User account is not verified");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new ValidationException("New password must be different from the current password");
        }

        if (!UserValidation.isValidPassword(newPassword)) {
            throw new ValidationException(
                    "New password does not meet security requirements. Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 special character, 1 number.");
        }

        user.setPasswordResetToken(null);
        user.setPasswordResetExpiration(null);
        userRepository.save(user);
    }

    public void verifyUser(VerifyUserModel input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getStatus() == UserStatus.verified) {
                throw new ValidationException("Account is already verified");
            }
            if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new TimeOutException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setStatus(UserStatus.verified);
                user.setVerificationCode(null);
                user.setVerificationExpiration(null);
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                throw new ValidationException("Invalid verification code");
            }
        } else {
            throw new ModelNotFoundException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new UserStatusException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new ModelNotFoundException("User not found");
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
                + "<a href=\"http://localhost:8080/auth/verify?email=" + user.getEmail()
                + "\" style=\"font-size: 18px; font-weight: bold; color: #007bff;\">Verify Account</a>"
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
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + user.getPasswordResetToken();
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