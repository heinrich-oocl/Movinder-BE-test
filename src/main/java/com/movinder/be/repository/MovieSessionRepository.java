package com.movinder.be.repository;

import com.movinder.be.entity.MovieSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieSessionRepository extends MongoRepository<MovieSession, String> {
    List<MovieSession> findByMovieId(String movieId);
}
