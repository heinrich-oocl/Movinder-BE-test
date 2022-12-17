package com.movinder.be.repository;

import com.movinder.be.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.util.Streamable;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findBymovieNameIgnoreCaseContaining(String movieName, Pageable pageable);
    Streamable<Movie> findBymovieNameIgnoreCaseContaining(String movieName);

    Streamable<Movie> findBylastShowDateTimeAfter(LocalDateTime date);

    List<Movie> findBymovieNameIgnoreCaseContainingAndLastShowDateTimeBetween(String movieName, LocalDateTime from, LocalDateTime to, Pageable pageable);


}