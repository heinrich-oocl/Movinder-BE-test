package com.movinder.be.controller;

import com.movinder.be.entity.Cinema;
import com.movinder.be.entity.Customer;
import com.movinder.be.entity.MovieSession;
import com.movinder.be.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/cinema")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Cinema> getCinema(@RequestParam(defaultValue = "") String cinemaName,
                                  @RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "20") Integer pageSize){
        return movieService.getCinema(cinemaName, page, pageSize);
    }

    @GetMapping("/cinema/{cinemaID}")
    @ResponseStatus(code = HttpStatus.OK)
    public Cinema getCinemaById(@PathVariable String cinemaID){

        return movieService.findCinemaById(cinemaID);
    }

    // TESTING USE ONLY
    @PostMapping("/cinema")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Cinema addCinema(@RequestBody Cinema cinema){
        return movieService.addCinema(cinema);
    }

    //film


    //session
    @PostMapping("/session")
    @ResponseStatus(code = HttpStatus.CREATED)
    public MovieSession addMovieSession(@RequestBody MovieSession movieSession){
        return movieService.addMovieSession(movieSession);
    }


    @GetMapping("/session/{sessionID}")
    @ResponseStatus(code = HttpStatus.OK)
    public MovieSession getSessionById(@PathVariable String sessionID){
        return movieService.findMovieSessionById(sessionID);
    }


}
