package com.myproject.busticket.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.dto.AccountDTO;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.services.AccountService;
import com.myproject.busticket.services.RoleService;

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
    public ResponseEntity<Map<String, Object>> getAccountDetails(@RequestParam int accountId) {
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

    @GetMapping("/roles")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRoles(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> rolePage = roleService.getAll(pageable);

        List<Role> roles = rolePage.getContent().stream()
                .map(role -> new Role(role.getRoleId(), role.getRoleName()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("roles", roles);
        response.put("currentPage", page);
        response.put("totalPages", rolePage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
