package com.example.demo.entity;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="prices")
public class Prices2 {
    @Id
    private String _id;
    private String id;
    private PriceInfor[] prices;
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public PriceInfor[] getPrices() {
        return prices;
    }
    public void setPrices(PriceInfor[] prices) {
        this.prices = prices;
    }
    @Override
    public String toString() {
        return "Prices2 [_id=" + _id + ", id=" + id + ", prices=" + Arrays.toString(prices) + "]";
    }
    
    
    
    
    

}
