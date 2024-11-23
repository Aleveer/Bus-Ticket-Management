package com.myproject.busticket.services;

import com.myproject.busticket.models.Customer;
import com.myproject.busticket.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public int findIDByEmail(String email) {
        return customerRepository.findByEmail(email).getCustomerId();
    }

    public boolean hasCustomer(String email) {
        return customerRepository.findByEmail(email) != null;
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
