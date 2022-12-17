package com.movinder.be.service;

import com.movinder.be.entity.Food;
import com.movinder.be.exception.IdNotFoundException;
import com.movinder.be.exception.MalformedRequestException;
import com.movinder.be.exception.ProvidedKeyAlreadyExistException;
import com.movinder.be.exception.RequestDataNotCompleteException;
import com.movinder.be.repository.FoodRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingService {
    private final FoodRepository foodMongoRepository;

    public BookingService(FoodRepository foodMongoRepository){
        this.foodMongoRepository = foodMongoRepository;
    }

    /*
    Food
     */

    public Food createFood(Food food){
        if (food.getFoodId() != null){
            throw new MalformedRequestException("Create food request should not contain ID");
        }
        try {
            return foodMongoRepository.save(food);
        } catch (DuplicateKeyException customerExist) {
            throw new ProvidedKeyAlreadyExistException("Food name");
        }
    }

    public List<Food> getFood(String foodName, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return foodMongoRepository.findByfoodNameIgnoringCaseContaining(foodName, pageable);
    }

    public Food findFoodById(String foodId){
        Utility.validateID(foodId);
        return foodMongoRepository.findById(foodId).orElseThrow(() -> new IdNotFoundException("Food"));
    }

    /*
    Checking
     */


    // checks if object contains null attributes
    private void validateFoodAttributes(Food food) {

        boolean containsNull = Stream
                .of(food.getFoodName(), food.getPrice(), food.getDescription())
                .anyMatch(Objects::isNull);

        if (containsNull){
            throw new RequestDataNotCompleteException("Food");
        }
    }

}
