package com.myproject.busticket.controllers;

import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.DriverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    DriverService driverService;
    CustomerService customerService;

    public ApiController(DriverService driverService, CustomerService customerService) {
        this.driverService = driverService;
        this.customerService = customerService;
    }

    @GetMapping("/api/driver/{driverId}")
    public Driver getDriverById(@PathVariable int driverId) {
        return driverService.getDriverById(driverId);
    }

    @GetMapping("/api/customer/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId) {
        return customerService.getCustomerById(customerId);
    }
}
