package com.myproject.busticket.services;

import jakarta.mail.MessagingException;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.VerifyAccountModel;
import com.myproject.busticket.models.LoginUserModel;
import com.myproject.busticket.models.RegisterUserModel;
import com.myproject.busticket.models.Role;
import com.myproject.busticket.enums.AccountStatus;
import com.myproject.busticket.exceptions.ModelNotFoundException;
import com.myproject.busticket.exceptions.TimeOutException;
import com.myproject.busticket.exceptions.AccountStatusException;
import com.myproject.busticket.exceptions.ValidationException;
import com.myproject.busticket.repositories.RoleRepository;
import com.myproject.busticket.repositories.AccountRepository;
import com.myproject.busticket.validations.AccountValidation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    public AuthenticationService(
            AccountRepository accountRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public Account signUp(RegisterUserModel input) {
        Account account = new Account();
        if (!AccountValidation.isValidEmail(input.getEmail())) {
            throw new ValidationException("Invalid email");
        }

        if (accountService.getUserByEmail(input.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        }

        if (AccountValidation.isValidPassword(input.getPassword())) {
            account.setPassword(passwordEncoder.encode(input.getPassword()));
        } else {
            throw new ValidationException(
                    "Invalid password. Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 special character, 1 number.");
        }
        account.setPassword(passwordEncoder.encode(input.getPassword()));
        // user.setFullName("Nguyen Van A");
        // user.setPhone("01234567890");
        Role role = roleRepository.findByRoleName("customer").orElseThrow(() -> new RuntimeException("Role not found"));
        account.setRole(role);
        account.setStatus(AccountStatus.unverified);
        account.setVerificationCode(generateVerificationCode());
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);
        account.setVerificationExpiration(expirationTime);
        sendVerificationEmail(account);
        return accountRepository.save(account);
    }

    public Account signIn(LoginUserModel input) {
        Account account = accountRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new AccountStatusException("An account is not found"));
        if (account.getStatus() == AccountStatus.unverified) {
            throw new AccountStatusException("Account not verified. Please verify your account.");
        }
        if (account.getStatus() == AccountStatus.banned) {
            throw new AccountStatusException("Account is banned. Please contact customer support.");
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
        return account;
    }

    public void signOut(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ModelNotFoundException("User not found"));

        if (account.getLoginToken() == null) {
            throw new ValidationException("User is not logged in");
        }

        account.setLoginToken(null);
        accountRepository.save(account);
    }

    public void requestPasswordReset(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ModelNotFoundException("An email was not found in our system. Please try again."));

        if (!AccountValidation.isValidEmail(email)) {
            throw new ValidationException("Invalid email address");
        }

        if (!account.isEnabled()) {
            throw new AccountStatusException("User account is disabled.");
        }

        if (account.getStatus() == AccountStatus.unverified) {
            throw new AccountStatusException("An account is not verified.");
        }

        if (account.getStatus() == AccountStatus.banned) {
            throw new AccountStatusException("An account is banned. Please contact customer support.");
        }

        String token = UUID.randomUUID().toString();
        account.setPasswordResetToken(token);
        account.setPasswordResetExpiration(LocalDateTime.now().plusMinutes(15));
        accountRepository.save(account);

        sendPasswordResetEmail(account);
    }

    public void resetPassword(String token, String newPassword) {
        Account account = accountRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid password reset token"));

        if (account.getPasswordResetExpiration().isBefore(LocalDateTime.now())) {
            throw new AccountStatusException("Password reset token has expired");
        }

        if (!account.isEnabled()) {
            throw new AccountStatusException("User account is disabled");
        }

        if (account.getStatus() != AccountStatus.verified) {
            throw new AccountStatusException("User account is not verified");
        }

        if (passwordEncoder.matches(newPassword, account.getPassword())) {
            throw new ValidationException("New password must be different from the current password");
        }

        if (!AccountValidation.isValidPassword(newPassword)) {
            throw new ValidationException(
                    "New password does not meet security requirements. Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 special character, 1 number.");
        }

        account.setPasswordResetToken(null);
        account.setPasswordResetExpiration(null);
        accountRepository.save(account);
    }

    public void verifyAccount(VerifyAccountModel input) {
        Optional<Account> optionalUser = accountRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            Account account = optionalUser.get();
            if (account.getStatus() == AccountStatus.verified) {
                throw new ValidationException("Account is already verified");
            }
            if (account.getVerificationExpiration().isBefore(LocalDateTime.now())) {
                throw new TimeOutException("Verification code has expired");
            }
            if (account.getVerificationCode().equals(input.getVerificationCode())) {
                account.setStatus(AccountStatus.verified);
                account.setVerificationCode(null);
                account.setVerificationExpiration(null);
                account.setEnabled(true);
                accountRepository.save(account);
            } else {
                throw new ValidationException("Invalid verification code");
            }
        } else {
            throw new ModelNotFoundException("An account is not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<Account> optionalUser = accountRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Account account = optionalUser.get();
            if (account.isEnabled()) {
                throw new AccountStatusException("Account is already verified");
            }
            account.setVerificationCode(generateVerificationCode());
            account.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(account);
            accountRepository.save(account);
        } else {
            throw new ModelNotFoundException("Account is not found");
        }
    }

    private void sendVerificationEmail(Account account) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + account.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "<a href=\"http://localhost:8080/auth/verify?email=" + account.getEmail()
                + "\" style=\"font-size: 18px; font-weight: bold; color: #007bff;\">Verify Account</a>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(account.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }

    // TODO: Update URL here if needed
    private void sendPasswordResetEmail(Account account) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + account.getPasswordResetToken();
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
            emailService.sendVerificationEmail(account.getEmail(), subject, htmlMessage);
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