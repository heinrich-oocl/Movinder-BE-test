package com.movinder.be.repository;

import com.movinder.be.entity.Cinema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CinemaRepository extends MongoRepository<Cinema, String> {
    List<Cinema> findBycinemaNameIgnoreCaseContaining(String keyword, Pageable pageable);

    //  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);

}
