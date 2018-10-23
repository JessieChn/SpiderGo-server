package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class PriceInfor {
    @Field("price_value")
    private String priceValue;
    @Field("date")
    private String date;
    public String getPriceValue() {
        return priceValue;
    }
    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "PriceInfor [priceValue=" + priceValue + ", date=" + date + "]";
    }
    
}
