package com.movinder.be.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Document
public class Message {
    @MongoId(FieldType.OBJECT_ID)
    private String messageId;
    private String customerId;
    private String message;
    private LocalDateTime createdTime;

    public Message(String customerId, String message){
        this.customerId = customerId;
        this.message = message;
        this.createdTime = LocalDateTime.now();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Long getEpochTime(){
        return this.getCreatedTime().toEpochSecond(ZoneOffset.UTC);
    }
}
