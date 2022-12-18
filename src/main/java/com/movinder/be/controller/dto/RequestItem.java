package com.movinder.be.controller.dto;

public class RequestItem {
    private String item;
    private Integer quantity;

    public RequestItem(String item, Integer quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
