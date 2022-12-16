package com.movinder.be.service;

import com.movinder.be.controller.dto.CustomerAuthenticateRequest;
import com.movinder.be.entity.Customer;
import com.movinder.be.exception.CustomerDataNotCompleteException;
import com.movinder.be.exception.CustomerNameAlreadyExistException;
import com.movinder.be.exception.CustomerNotFoundException;
import com.movinder.be.exception.WrongCredentialsException;
import com.movinder.be.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class CustomerService {
    private final CustomerRepository customerMongoRepository;

    public CustomerService(CustomerRepository customerMongoRepository) {
        this.customerMongoRepository = customerMongoRepository;
    }

    public Customer authenticate(CustomerAuthenticateRequest customerAuthenticateRequest) {

        if (!customerMongoRepository.existsByName(customerAuthenticateRequest.getUsername())) {
            throw new WrongCredentialsException();
        }

        Customer customer = customerMongoRepository.findByName(customerAuthenticateRequest.getUsername());
        if (customer.getPassword().equals(customerAuthenticateRequest.getPassword())) {
            return customer;
        } else {
            throw new WrongCredentialsException();
        }
    }


    public Customer createCustomerAccount(Customer customer) {
        if (customerMongoRepository.existsByName(customer.getName())) {
            throw new CustomerNameAlreadyExistException();
        }
        return customerMongoRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        boolean containsNullValue =
                Stream
                        .of(customer.getName(), customer.getPassword(), customer.getGender(), customer.getAge(),
                                customer.getStatus(), customer.getSelfIntro(), customer.getShowName(),
                                customer.getShowGender(), customer.getShowAge(), customer.getShowStatus())
                        .anyMatch(Objects::isNull);

        if (containsNullValue) {
            throw new CustomerDataNotCompleteException();
        }
        if (!customerMongoRepository.existsById(customer.getCustomerId())) {
            throw new CustomerNotFoundException();
        }
        return customerMongoRepository.save(customer);

    }

}
