package com.movinder.be.repository;

import com.movinder.be.entity.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByCustomerIdAndBookingTimeBetween(String customerId, LocalDateTime from, LocalDateTime to, Pageable pageable);

}
