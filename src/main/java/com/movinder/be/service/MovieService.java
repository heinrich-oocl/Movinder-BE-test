package com.movinder.be.service;

import com.movinder.be.entity.Cinema;
import com.movinder.be.entity.MovieSession;
import com.movinder.be.entity.Seat;
import com.movinder.be.exception.Cinema.CinemaDataNotCompleteException;
import com.movinder.be.exception.Cinema.CinemaNotFoundException;
import com.movinder.be.exception.MovieSession.MovieSessionDataNotCompleteException;
import com.movinder.be.exception.MovieSession.SeatOccupiedExcpetion;
import com.movinder.be.exception.MovieSession.SessionNotFoundError;
import com.movinder.be.repository.CinemaRepository;
import com.movinder.be.repository.MovieSessionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class MovieService {
    private final CinemaRepository cinemaRepository;
    private final MovieSessionRepository movieSessionRepository;

    public MovieService(CinemaRepository cinemaRepository, MovieSessionRepository movieSessionRepository){
        this.cinemaRepository = cinemaRepository;
        this.movieSessionRepository = movieSessionRepository;
    }


    public Cinema addCinema(Cinema cinema){
        validateCinemaAttributes(cinema);
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> getCinema(String cinemaName, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return cinemaRepository.findBycinemaNameIgnoreCaseContaining(cinemaName, pageable);

    }

    public Cinema findCinemaById(String id){
        Utility.validateID(id);
        return cinemaRepository.findById(id).orElseThrow(CinemaNotFoundException::new);
    }

    public MovieSession addMovieSession(MovieSession movieSession){

        // use the default floor plan
        movieSession.setAvailableSeatings(
                findCinemaById(movieSession.getCinemaId()).getFloorPlan());
        validateMovieSessionAttributes(movieSession);
        return movieSessionRepository.save(movieSession);
    }

    public MovieSession findMovieSessionById(String id){
        Utility.validateID(id);
        return movieSessionRepository.findById(id).orElseThrow(SessionNotFoundError::new);
    }

    // TESTING ONLY
    public MovieSession updateMovieSession(MovieSession movieSession){
        Utility.validateID(movieSession.getSessionId());
        validateMovieSessionAttributes(movieSession);
        return movieSessionRepository.save(movieSession);
    }

    public MovieSession bookSeats(String sessionId, List<Seat> seats){
        MovieSession session = movieSessionRepository.findById(sessionId).orElseThrow(SessionNotFoundError::new);

        ArrayList<ArrayList<Boolean>> avaialbleSeatings = session.getAvailableSeatings();

        seats.forEach(seat -> {
            int row = seat.getRow();
            int col = seat.getColumn();
            Boolean available = avaialbleSeatings.get(row).get(col);
            if (available) {
                avaialbleSeatings.get(row).set(col, false);
            } else {
                throw new SeatOccupiedExcpetion();
            }
        });

        session.setAvailableSeatings(avaialbleSeatings);
        return movieSessionRepository.save(session);

    }

    // checks if object contains null attributes
    private void validateMovieSessionAttributes(MovieSession movieSession) {

        boolean containsNull = Stream
                .of(movieSession.getAvailableSeatings(),
                        movieSession.getDatetime(),
                        movieSession.getCinemaId())
                .anyMatch(Objects::isNull);

        if (containsNull){
            throw new MovieSessionDataNotCompleteException();
        }

    }

    // checks if object contains null attributes
    private void validateCinemaAttributes(Cinema cinema) {

        boolean containsNull = Stream
                .of(cinema.getAddress(),
                        cinema.getCinemaName(),
                        cinema.getFloorPlan())
                .anyMatch(Objects::isNull);

        if (containsNull){
            throw new CinemaDataNotCompleteException();
        }

    }

}
