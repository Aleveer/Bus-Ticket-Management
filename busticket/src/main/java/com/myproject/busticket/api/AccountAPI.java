package com.myproject.busticket.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.myproject.busticket.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.myproject.busticket.models.Account;
import com.myproject.busticket.services.AccountService;
import com.myproject.busticket.services.RoleService;
import com.myproject.busticket.utilities.SecurityUtil;
import com.myproject.busticket.validations.AccountValidation;

@RestController
@RequestMapping("/api/account")
public class AccountAPI {
    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/accounts")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAccounts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountPages = accountService.getAll(pageable);

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

    @PostMapping("/update-account")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateAccount(@RequestBody Map<String, Object> updatedAccount) {
        Map<String, Object> response = new HashMap<>();

        try {
            int accountId = (int) updatedAccount.get("accountId");
            Account account = accountService.getById(accountId);
            if (account == null) {
                response.put("message", "Account not found.");
                return ResponseEntity.badRequest().body(response);
            }

            // check current logged in account with the account to be updated, if it's the
            // same, return unauthorized
            Account currentAccount = SecurityUtil.getCurrentAccount();
            if (currentAccount.getId() == accountId) {
                response.put("message", "Unauthorized to update account.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String role = (String) updatedAccount.get("role");
            if (role == null) {
                response.put("message", "Role is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (!AccountRole.admin.toString().equals(role) && !AccountRole.customer.toString().equals(role) &&
                    !AccountRole.controller.toString().equals(role) && !AccountRole.driver.toString().equals(role) &&
                    !AccountRole.staff.toString().equals(role)) {
                response.put("message", "Invalid role.");
                return ResponseEntity.badRequest().body(response);
            }

            String fullname = (String) updatedAccount.get("fullname");
            if (fullname == null) {
                response.put("message", "Fullname is required.");
                return ResponseEntity.badRequest().body(response);
            }

            String phoneNumber = (String) updatedAccount.get("phone");
            if (phoneNumber == null) {
                response.put("message", "Phone number is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (AccountValidation.isValidPhone(phoneNumber)) {
                response.put("message", "Invalid phone number format.");
                return ResponseEntity.badRequest().body(response);
            }

            String status = (String) updatedAccount.get("status");
            if (status == null) {
                response.put("message", "Status is required.");
                return ResponseEntity.badRequest().body(response);
            }

            response.put("message", "Account updated successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error updating account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/new-account")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> newAccount(@RequestBody Map<String, Object> newAccountData) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Lấy dữ liệu từ request
            String email = (String) newAccountData.get("email");
            String role = (String) newAccountData.get("role");
            String fullName = (String) newAccountData.get("fullname");
            String phone = (String) newAccountData.get("phone");
            String status = (String) newAccountData.get("status");
            String password = (String) newAccountData.get("password");

            // Kiểm tra tính hợp lệ của dữ liệu
            if (email == null || email.isEmpty()) {
                response.put("message", "Email is required.");
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

            // Tạo đối tượng Account mới
            Account newAccount = new Account();
            newAccount.setEmail(email);
            newAccount.setRole(roleService.getRoleByName(role));
            newAccount.setFullName(fullName);
            newAccount.setPhone(phone);
            newAccount.setStatus(AccountStatus.valueOf(status)); // Giả sử status là một enum.
            newAccount.setPassword(password); // Nên mã hóa mật khẩu trước khi lưu.

            // Lưu tài khoản vào cơ sở dữ liệu
            accountService.save(newAccount);

            // Phản hồi thành công
            response.put("message", "Account created successfully.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Xử lý lỗi chung
            response.put("message", "Error creating account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
