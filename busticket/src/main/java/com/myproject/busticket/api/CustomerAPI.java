package com.myproject.busticket.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.myproject.busticket.dto.CustomerDTO;
import com.myproject.busticket.models.Customer;
import com.myproject.busticket.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPI {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/guests")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getGuests(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPages = customerService.getAll(pageable);

        List<CustomerDTO> customerDTOs = customerPages.getContent().stream()
                .map(customer -> new CustomerDTO(
                        customer.getCustomerId(),
                        customer.getEmail(),
                        customer.getName(),
                        customer.getPhone()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("guests", customerDTOs);
        response.put("currentPage", page);
        response.put("totalPages", customerPages.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
