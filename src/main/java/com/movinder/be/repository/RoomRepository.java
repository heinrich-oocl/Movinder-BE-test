package com.movinder.be.repository;

import com.movinder.be.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {
    Optional<Room> findByMovieId(String movieId);
    List<Room> findByCustomerIdsContaining(String target);
}
