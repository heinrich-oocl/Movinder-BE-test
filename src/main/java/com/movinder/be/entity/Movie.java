package com.movinder.be.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document
public class Movie {
    @MongoId(FieldType.OBJECT_ID)
    private String movieId;

    @Indexed(unique = true)
    private String movieName;

    private String description;
    private Integer duration; // in minutes
    private ArrayList<String> movieSessionIds;
    @Indexed
    private LocalDateTime lastShowDateTime;
    private String thumbnailUrl;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ArrayList<String> getMovieSessionIds() {
        return movieSessionIds;
    }

    public void setMovieSessionIds(ArrayList<String> movieSessionIds) {
        this.movieSessionIds = movieSessionIds;
    }

    public LocalDateTime getLastShowDateTime() {
        return lastShowDateTime;
    }

    public void setLastShowDateTime(LocalDateTime lastShowDateTime) {
        this.lastShowDateTime = lastShowDateTime;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
