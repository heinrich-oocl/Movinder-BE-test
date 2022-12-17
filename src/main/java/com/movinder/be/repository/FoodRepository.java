package com.movinder.be.repository;

import com.movinder.be.entity.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food, String> {
    List<Food> findByfoodNameIgnoringCaseContaining(String foodName, Pageable pageable);
}
