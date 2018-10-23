package com.example.demo.entity;

import java.util.Arrays;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class SalePromotionInfor {
    @Field("sale_promotion_value")
    private String[] salePromotionValue;
    @Field("date")
    private String date;
    
    
    public String[] getSalePromotionValue() {
        return salePromotionValue;
    }
    public void setSalePromotionValue(String[] salePromotionValue) {
        this.salePromotionValue = salePromotionValue;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "SalePromotionInfor [salePromotionValue=" + Arrays.toString(salePromotionValue) + ", date=" + date + "]";
    }
    
    
    
}
