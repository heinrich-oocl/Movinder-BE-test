package com.movinder.be.repository;

import com.movinder.be.entity.MovieSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieSessionRepository extends MongoRepository<MovieSession, String> {
}
