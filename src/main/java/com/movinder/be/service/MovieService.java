package com.movinder.be.service;

import com.movinder.be.entity.Cinema;
import com.movinder.be.entity.Movie;
import com.movinder.be.entity.MovieSession;
import com.movinder.be.entity.Seat;
import com.movinder.be.exception.IdNotFoundException;
import com.movinder.be.exception.MalformedRequestException;
import com.movinder.be.exception.MovieSession.SeatOccupiedExcpetion;
import com.movinder.be.exception.RequestDataNotCompleteException;
import com.movinder.be.repository.CinemaRepository;
import com.movinder.be.repository.MovieRepository;
import com.movinder.be.repository.MovieSessionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class MovieService {
    private final CinemaRepository cinemaRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final MovieRepository movieRepository;

    public MovieService(CinemaRepository cinemaRepository, MovieSessionRepository movieSessionRepository, MovieRepository movieRepository){
        this.cinemaRepository = cinemaRepository;
        this.movieSessionRepository = movieSessionRepository;
        this.movieRepository = movieRepository;
    }

    /*
    Cinema
     */

    public Cinema addCinema(Cinema cinema){
        if (cinema.getCinemaId() != null){
            throw new MalformedRequestException("Create cinema request should not contain ID");
        }
        validateCinemaAttributes(cinema);
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> getCinema(String cinemaName, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return cinemaRepository.findBycinemaNameIgnoreCaseContaining(cinemaName, pageable);

    }

    public Cinema findCinemaById(String id){
        Utility.validateID(id);
        return cinemaRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Cinema"));
    }

    /*
    Movie Session
     */

    public MovieSession addMovieSession(MovieSession movieSession){
        if (movieSession.getSessionId() != null){
            throw new MalformedRequestException("Create movie session request should not contain ID");
        }
        // use the default floor plan
        movieSession.setAvailableSeatings(
                findCinemaById(movieSession.getCinemaId()).getFloorPlan());
        validateMovieSessionAttributes(movieSession);

        // check if movie exist, if not will throw exception
        findMovieById(movieSession.getMovieId());

        MovieSession savedMovieSession = movieSessionRepository.save(movieSession);
        updateMovieInfo(savedMovieSession);

        return savedMovieSession;
    }

    public MovieSession findMovieSessionById(String id){
        Utility.validateID(id);
        return movieSessionRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Movie Session"));
    }

    // TESTING ONLY
    public MovieSession updateMovieSession(MovieSession movieSession){
        Utility.validateID(movieSession.getSessionId());
        validateMovieSessionAttributes(movieSession);

        updateMovieInfo(movieSession);

        return movieSessionRepository.save(movieSession);
    }

    public MovieSession bookSeats(String sessionId, List<Seat> seats){
        MovieSession session = movieSessionRepository.findById(sessionId).orElseThrow(() -> new IdNotFoundException("Movie Session"));

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



    /*
    Movie
     */

    public Movie addMovie(Movie movie){
        if (movie.getMovieId() != null){
            throw new MalformedRequestException("Create movie session request should not contain ID");
        }
        movie.setMovieSessionIds(new ArrayList<>());
        validateMovieAttributes(movie);
        return movieRepository.save(movie);
    }

    public List<Movie> getMovie(String movieName, Integer page, Integer pageSize, String from, String to){
        LocalDateTime fromDate = from == null ? LocalDateTime.now() : LocalDateTime.parse(from);
        LocalDateTime toDate = to == null ? LocalDateTime.now().plusMonths(1) : LocalDateTime.parse(to);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "lastShowDateTime");

        return movieRepository
                .findBymovieNameIgnoreCaseContainingAndLastShowDateTimeBetween(movieName, fromDate, toDate, pageable);

    }

    public Movie findMovieById(String id){
        Utility.validateID(id);
        return movieRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Movie"));
    }

    // update lastShowTime and session ID
    private void updateMovieInfo(MovieSession movieSession){
        Movie movie = movieRepository
                .findById(movieSession.getMovieId())
                .orElseThrow(() -> new IdNotFoundException("Movie"));
        if (movieSession.getDatetime().isAfter(movie.getLastShowDateTime())){
            movie.setLastShowDateTime(movieSession.getDatetime());
        }
        if (!movie.getMovieSessionIds().contains(movieSession.getSessionId())){
            movie.getMovieSessionIds().add(movieSession.getSessionId());
        }
        movieRepository.save(movie);
    }

    /*
    Checking
     */
    // checks if object contains null attributes
    private void validateMovieSessionAttributes(MovieSession movieSession) {

        boolean containsNull = Stream
                .of(movieSession.getAvailableSeatings(),
                        movieSession.getDatetime(),
                        movieSession.getCinemaId())
                .anyMatch(Objects::isNull);

        if (containsNull){
            throw new RequestDataNotCompleteException("Movie Session");
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
            throw new RequestDataNotCompleteException("Cinema");
        }

    }

    private void validateMovieAttributes(Movie movie){
        boolean containsNull = Stream
                .of(movie.getMovieName(),
                        movie.getDescription(),
                        movie.getDuration(),
                        movie.getLastShowDateTime())
                .anyMatch(Objects::isNull);

        if (containsNull){
            throw new RequestDataNotCompleteException("Movie");
        }

    }

}
