package com.movinder.be.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

@Document
public class Cinema {
    @MongoId(FieldType.OBJECT_ID)
    private String cinemaId;
    @Indexed(unique = true)
    private String cinemaName;
    private String address;
    private ArrayList<ArrayList<Boolean>> floorPlan;

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<ArrayList<Boolean>> getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(ArrayList<ArrayList<Boolean>> floorPlan) {
        this.floorPlan = floorPlan;
    }
}
