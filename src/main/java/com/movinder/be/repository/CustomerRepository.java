package com.movinder.be.repository;

import com.movinder.be.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByCustomerName(String customerName);

    Boolean existsByCustomerName(String customerName);
}
