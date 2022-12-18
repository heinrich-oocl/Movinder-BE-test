package com.movinder.be.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class MovieSession {
    @MongoId(FieldType.OBJECT_ID)
    private String sessionId;
    private LocalDateTime datetime;
    private ArrayList<ArrayList<Boolean>> availableSeatings;
    private String cinemaId;
    private String movieId;
    private ArrayList<Pricing> pricing;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public ArrayList<ArrayList<Boolean>> getAvailableSeatings() {
        return availableSeatings;
    }

    public void setAvailableSeatings(ArrayList<ArrayList<Boolean>> availableSeatings) {
        this.availableSeatings = availableSeatings;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public ArrayList<Pricing> getPricing() {
        return pricing;
    }

    public void setPricing(ArrayList<Pricing> pricing) {
        this.pricing = pricing;
    }
}
