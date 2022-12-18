package com.movinder.be.entity;

public class Pricing {
    private String item;
    private Integer price;

    public Pricing(String item, Integer price){
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
