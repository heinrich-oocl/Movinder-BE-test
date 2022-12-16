package com.movinder.be.service;


import com.movinder.be.controller.dto.CustomerAuthenticateRequest;
import com.movinder.be.entity.Customer;
import com.movinder.be.exception.Customer.CustomerDataNotCompleteException;
import com.movinder.be.exception.InvalidIDException;
import com.movinder.be.exception.Customer.CustomerNameAlreadyExistException;
import com.movinder.be.exception.Customer.CustomerNotFoundException;
import com.movinder.be.exception.Customer.WrongCredentialsException;
import com.movinder.be.exception.MalformedRequestException;
import com.movinder.be.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
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

        if (!customerMongoRepository.existsByCustomerName(customerAuthenticateRequest.getUsername())) {
            throw new WrongCredentialsException();
        }

        Customer customer = customerMongoRepository.findByCustomerName(customerAuthenticateRequest.getUsername());
        if (customer.getPassword().equals(customerAuthenticateRequest.getPassword())) {
            return customer;
        } else {
            throw new WrongCredentialsException();
        }
    }


    public Customer createCustomerAccount(Customer customer) {

        if (customer.getCustomerId() != null){
            throw new MalformedRequestException("Create customer request should not contain ID");
        }
        validateCustomerAttributes(customer);

        System.out.println(customer.getCustomerName());

        try {
            return customerMongoRepository.save(customer);
        } catch (DuplicateKeyException customerExist) {
            throw new CustomerNameAlreadyExistException();
        }
    }

    public Customer updateCustomer(Customer customer) {

        validateCustomerID(customer);
        validateCustomerAttributes(customer);
        if (!customerMongoRepository.existsById(customer.getCustomerId())) {
            throw new CustomerNotFoundException();
        }
        return customerMongoRepository.save(customer);

    }

    // checks if object contains null attributes
    private void validateCustomerAttributes(Customer customer) {

        boolean containsNull = Stream
                .of(customer.getCustomerName(), customer.getPassword(), customer.getGender(), customer.getAge(),
                        customer.getStatus(), customer.getSelfIntro(), customer.getShowName(),
                        customer.getShowGender(), customer.getShowAge(), customer.getShowStatus())
                .anyMatch(Objects::isNull);

        if (containsNull){
            throw new CustomerDataNotCompleteException();
        }

    }

    //  valid if ID is valid Object ID
    private void validateCustomerID(Customer customer){
        if (!ObjectId.isValid(customer.getCustomerId())){
            throw new InvalidIDException();
        }
    }

}
