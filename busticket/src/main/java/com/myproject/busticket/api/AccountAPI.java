package com.myproject.busticket.api;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.dto.AccountDTO;
import com.myproject.busticket.enums.AccountRole;
import com.myproject.busticket.enums.AccountStatus;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Controller;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.models.Staff;
import com.myproject.busticket.services.AccountService;
import com.myproject.busticket.services.AuthenticationService;
import com.myproject.busticket.services.ControllerService;
import com.myproject.busticket.services.DriverService;
import com.myproject.busticket.services.JwtService;
import com.myproject.busticket.services.RoleService;
import com.myproject.busticket.services.StaffService;
import com.myproject.busticket.services.TokenStoreService;
import com.myproject.busticket.utilities.SecurityUtil;
import com.myproject.busticket.validations.AccountValidation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/account")
public class AccountAPI {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ControllerService controllerService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenStoreService tokenStoreService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<Account> authenticatedAccount() {
        Account currentAccount = SecurityUtil.getCurrentAccount();
        if (currentAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(currentAccount);
    }

    @GetMapping("/accounts")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) String searchValue) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPages;

        if (searchValue != null && !searchValue.isEmpty()) {
            accountPages = accountService.searchAccountsByEmailAndNameAndPhone(pageable, searchValue);
        } else {
            accountPages = accountService.getAll(pageable);
        }

        List<AccountDTO> accountDTOs = accountPages.getContent().stream()
                .map(account -> new AccountDTO(account.getId(), account.getEmail(),
                        account.getPassword(),
                        account.getFullName(), account.getPhone(),
                        roleService.getRoleById(account.getRole().getRoleId()),
                        account.getStatus(),
                        account.getVerificationCode(), account.getVerificationExpiration(),
                        account.getLoginToken(),
                        account.getPasswordResetToken(), account.getPasswordResetExpiration(),
                        account.isEnabled()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("accounts", accountDTOs);
        response.put("currentPage", page);
        response.put("totalPages", accountPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers-account")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCustomers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPages = accountService.getAll(pageable);

        List<AccountDTO> accountDTOs = accountPages.getContent().stream()
                .filter(account -> account.getRole().getRoleId() == 4)
                .map(account -> new AccountDTO(account.getId(), account.getEmail(),
                        account.getPassword(),
                        account.getFullName(), account.getPhone(),
                        roleService.getRoleById(account.getRole().getRoleId()),
                        account.getStatus(),
                        account.getVerificationCode(), account.getVerificationExpiration(),
                        account.getLoginToken(),
                        account.getPasswordResetToken(), account.getPasswordResetExpiration(),
                        account.isEnabled()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("accounts", accountDTOs);
        response.put("currentPage", page);
        response.put("totalPages", accountPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/current-user-id")
    public ResponseEntity<Integer> getCurrentUserId() {
        int userId = authenticationService.getCurrentUserId();
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/account-detail/{accountId}")
    public ResponseEntity<Map<String, Object>> getAccountDetails(@PathVariable int accountId) {
        Account account = accountService.getById(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        AccountDTO accountDTO = new AccountDTO(account.getId(), account.getEmail(),
                account.getPassword(),
                account.getFullName(), account.getPhone(),
                roleService.getRoleById(account.getRole().getRoleId()),
                account.getStatus(),
                account.getVerificationCode(), account.getVerificationExpiration(),
                account.getLoginToken(),
                account.getPasswordResetToken(), account.getPasswordResetExpiration(),
                account.isEnabled());

        Map<String, Object> response = new HashMap<>();
        response.put("account", accountDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-account/{accountId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateAccount(@PathVariable int accountId,
            @RequestBody Map<String, Object> updatedAccount, HttpServletResponse httpResponse) {
        Map<String, Object> response = new HashMap<>();

        try {
            Account account = accountService.getById(accountId);
            if (account == null) {
                response.put("success", false);
                response.put("message", "Account not found.");
                return ResponseEntity.badRequest().body(response);
            }

            Account currentAccount = SecurityUtil.getCurrentAccount();

            // Check if the current user has admin authority
            boolean isAdmin = SecurityUtil.getCurrentUserRoles().stream()
                    .anyMatch(role -> role.getAuthority().equals(AccountRole.admin.toString().toUpperCase()));

            // Admin role can't change other admin role but can change own account
            if (isAdmin && account.getRole().getRoleName().equalsIgnoreCase(AccountRole.admin.toString())
                    && currentAccount.getId() != accountId) {
                response.put("success", false);
                response.put("message", "You don't have permission to update another admin account.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Validate role
            String role = (String) updatedAccount.get("role");
            if (role == null || !EnumSet
                    .of(AccountRole.admin, AccountRole.customer, AccountRole.controller, AccountRole.driver,
                            AccountRole.staff)
                    .contains(AccountRole.valueOf(role))) {
                response.put("success", false);
                response.put("message", "Invalid or missing role.");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate fullname
            String fullname = (String) updatedAccount.get("fullName");
            if (fullname == null) {
                response.put("success", false);
                response.put("message", "Fullname is required.");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate phone number
            String phoneNumber = (String) updatedAccount.get("phone");
            if (phoneNumber == null || !AccountValidation.isValidPhone(phoneNumber)) {
                response.put("success", false);
                response.put("message", "Invalid or missing phone number.");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate status
            String status = (String) updatedAccount.get("status");
            if (status == null) {
                response.put("success", false);
                response.put("message", "Status is required.");
                return ResponseEntity.badRequest().body(response);
            }
            // Handle email if it's changed
            String newEmail = (String) updatedAccount.get("email");
            if (newEmail != null && !newEmail.equals(account.getEmail())) {
                if (!AccountValidation.isValidEmail(newEmail)) {
                    response.put("success", false);
                    response.put("message", "Invalid email format.");
                    return ResponseEntity.badRequest().body(response);
                }
                if (accountService.getUserByEmail(newEmail).isPresent()) {
                    response.put("success", false);
                    response.put("message", "Email already in use.");
                    return ResponseEntity.badRequest().body(response);
                }

                // Check if the current email is currently logged in
                String currentEmail = account.getEmail();
                if (tokenStoreService.isTokenActive(currentEmail)) {
                    // Invalidate the current JWT token for the current email
                    tokenStoreService.invalidateToken(currentEmail);
                }

                // Update the email in the account
                account.setEmail(newEmail);

                // Invalidate the current JWT token and issue a new one
                String newJwtToken = jwtService.generateToken(account);
                Cookie cookie = new Cookie("jwtToken", newJwtToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60);
                httpResponse.addCookie(cookie);
                response.put("jwtToken", newJwtToken);

                // Store the new token
                tokenStoreService.storeToken(newEmail, newJwtToken);
            }

            // Update account details
            account.setFullName(fullname);
            account.setPhone(phoneNumber);
            account.setStatus(AccountStatus.valueOf(status));

            // Update password if provided
            String newPassword = (String) updatedAccount.get("newPassword");
            if (newPassword != null && !newPassword.isEmpty()) {
                account.setPassword(passwordEncoder.encode(newPassword));
            }

            // Handle role-specific logic
            handleRoleSpecificLogic(account, role);

            account.setRole(roleService.getRoleByName(role));
            accountService.save(account);

            response.put("success", true);
            response.put("message", "Account updated successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void handleRoleSpecificLogic(Account account, String role) {
        if (AccountRole.driver.toString().equals(role)) {
            deleteControllerAndStaff(account);
            Driver driver = new Driver();
            driver.setAccount(account);
            driverService.save(driver);
        } else if (AccountRole.controller.toString().equals(role)) {
            deleteDriverAndStaff(account);
            Controller controller = new Controller();
            controller.setAccount(account);
            controllerService.save(controller);
        } else if (AccountRole.staff.toString().equals(role)) {
            deleteDriverAndController(account);
            Staff staff = new Staff();
            staff.setAccount(account);
            staffService.save(staff);
        }
    }

    private void deleteControllerAndStaff(Account account) {
        List<Controller> controller = controllerService.getControllerByAccount(account);
        if (controller.size() > 0) {
            controllerService.deleteController(controller.get(0));
        }

        List<Staff> staff = staffService.getStaffByAccount(account);
        if (staff.size() > 0) {
            staffService.deleteStaff(staff.get(0));
        }
    }

    private void deleteDriverAndStaff(Account account) {
        List<Driver> driver = driverService.getDriverByAccount(account);
        if (driver.size() > 0) {
            driverService.deleteDriver(driver.get(0));
        }

        List<Staff> staff = staffService.getStaffByAccount(account);
        if (staff.size() > 0) {
            staffService.deleteStaff(staff.get(0));
        }
    }

    private void deleteDriverAndController(Account account) {
        List<Driver> driver = driverService.getDriverByAccount(account);
        if (driver.size() > 0) {
            driverService.deleteDriver(driver.get(0));
        }

        List<Controller> controller = controllerService.getControllerByAccount(account);
        if (controller.size() > 0) {
            controllerService.deleteController(controller.get(0));
        }
    }

    @PostMapping("/new-account")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> newAccount(@RequestBody Map<String, Object> newAccountData) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate input data
            String email = (String) newAccountData.get("email");
            String role = (String) newAccountData.get("role");
            String fullName = (String) newAccountData.get("fullName");
            String phone = (String) newAccountData.get("phone");
            String status = (String) newAccountData.get("status");
            String password = (String) newAccountData.get("password");
            String confirmPassword = (String) newAccountData.get("confirmPassword");
            if (email == null || email.isEmpty()) {
                response.put("message", "Email is required.");
                return ResponseEntity.badRequest().body(response);
            }

            // check if email is valid:
            if (!AccountValidation.isValidEmail(email)) {
                response.put("message", "Invalid email format.");
                return ResponseEntity.badRequest().body(response);
            }

            if (role == null || role.isEmpty()) {
                response.put("message", "Role is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!AccountRole.admin.toString().equals(role) && !AccountRole.customer.toString().equals(role) &&
                    !AccountRole.controller.toString().equals(role) && !AccountRole.driver.toString().equals(role) &&
                    !AccountRole.staff.toString().equals(role)) {
                response.put("message", "Invalid role.");
                return ResponseEntity.badRequest().body(response);
            }

            if (fullName == null || fullName.isEmpty()) {
                response.put("message", "Fullname is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (phone == null || phone.isEmpty() || !AccountValidation.isValidPhone(phone)) {
                response.put("message", "Invalid phone number format.");
                return ResponseEntity.badRequest().body(response);
            }

            if (status == null || status.isEmpty()) {
                response.put("message", "Status is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (password == null || password.isEmpty()) {
                response.put("message", "Password is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (confirmPassword == null || confirmPassword.isEmpty()) {
                response.put("message", "Confirm password is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!password.equals(confirmPassword)) {
                response.put("message", "Passwords do not match.");
                return ResponseEntity.badRequest().body(response);
            }

            // check if password is valid:
            if (!AccountValidation.isValidPassword(password)) {
                response.put("message",
                        "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number and one special character.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!AccountValidation.isValidEmail(email)) {
                response.put("message", "Invalid email format.");
                return ResponseEntity.badRequest().body(response);
            }

            if (accountService.getUserByEmail(email).isPresent()) {
                response.put("message", "Email already in use.");
                return ResponseEntity.badRequest().body(response);
            }

            // Create new account
            Account newAccount = new Account();
            newAccount.setEmail(email);
            newAccount.setRole(roleService.getRoleByName(role));
            newAccount.setFullName(fullName);
            newAccount.setPhone(phone);
            newAccount.setStatus(AccountStatus.valueOf(status));
            newAccount.setPassword(passwordEncoder.encode(password));

            // Handle role-specific logic
            handleRoleSpecificLogic(newAccount, role);

            response.put("message", "Account created successfully.");
            if (AccountRole.admin.toString().equals(role)) {
                response.put("warning", "You have created an account with admin privileges.");
            }

            accountService.save(newAccount);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error creating account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/delete-account/{accountId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable int accountId) {
        Map<String, Object> response = new HashMap<>();

        try {
            Account account = accountService.getById(accountId);
            if (account == null) {
                response.put("message", "Account not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Account currentAccount = SecurityUtil.getCurrentAccount();
            if (currentAccount != null && currentAccount.getId() == accountId) {
                response.put("message", "You cannot delete your own account.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            boolean isAdmin = SecurityUtil.getCurrentUserRoles().stream()
                    .anyMatch(role -> role.getAuthority().equals(AccountRole.admin.toString().toUpperCase()));
            if (isAdmin && account.getRole().getRoleName().equalsIgnoreCase(AccountRole.admin.toString())) {
                response.put("message", "You can't delete another admin account.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            accountService.delete(accountId);
            response.put("message", "Account deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
