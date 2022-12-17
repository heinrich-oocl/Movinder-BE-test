package com.movinder.be.service;

import com.movinder.be.entity.Cinema;
import com.movinder.be.entity.Customer;
import com.movinder.be.exception.Cinema.CinemaNotFoundException;
import com.movinder.be.exception.InvalidIDException;
import com.movinder.be.repository.CinemaRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final CinemaRepository cinemaRepository;

    public MovieService(CinemaRepository cinemaRepository){
        this.cinemaRepository = cinemaRepository;
    }


    public Cinema addCinema(Cinema cinema){
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> getCinema(String cinemaName, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return cinemaRepository.findBycinemaNameIgnoreCaseContaining(cinemaName, pageable);

    }

    public Cinema findCinemaById(String id){
        validateCinemaID(id);
        return cinemaRepository.findById(id).orElseThrow(CinemaNotFoundException::new);
    }

    //  valid if ID is valid Object ID
    private void validateCinemaID(String id){
        if (!ObjectId.isValid(id)){
            throw new InvalidIDException();
        }
    }

}
