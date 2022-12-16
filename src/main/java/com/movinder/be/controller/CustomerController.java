package com.movinder.be.controller;

import com.movinder.be.controller.dto.CustomerAuthenticateRequest;
import com.movinder.be.entity.Customer;
import com.movinder.be.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer) {
        return customerService.createCustomerAccount(customer);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(code = HttpStatus.OK)
    public Customer authenticateAndGetCustomer(@RequestBody CustomerAuthenticateRequest customerAuthenticateRequest) {
        return customerService.authenticate(customerAuthenticateRequest);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public String test() {
        return "success in test";
    }

}
