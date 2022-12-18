package com.movinder.be.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

@Document
public class Room {
    @MongoId(FieldType.OBJECT_ID)
    private String roomId;
    private ArrayList<String> messageIds;
    private ArrayList<String> customerIds;

    @Indexed(unique = true)
    private String movieId;

    public Room(String movieId){
        this.messageIds = new ArrayList<>();
        this.customerIds = new ArrayList<>();
        this.movieId = movieId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public ArrayList<String> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(ArrayList<String> messageIds) {
        this.messageIds = messageIds;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void addMessageId(String messageId){
        this.messageIds.add(messageId);
    }

    public ArrayList<String> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(ArrayList<String> customerIds) {
        this.customerIds = customerIds;
    }

    public void addCustomerId(String customerId){
        if (!customerIds.contains(customerId)){
            this.customerIds.add(customerId);
        }
    }
}
