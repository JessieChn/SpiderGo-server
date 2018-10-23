package com.example.demo.entity;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="prices")
public class Prices {
    @Id
    private String _id;
    private String id;
    private PriceInfor[] prices;
    private SalePromotionInfor[] salePromotionInfors;
    private PhoneDisplay phoneDisplay;
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
    public PhoneDisplay getPhoneDisplay() {
        return phoneDisplay;
    }
    public void setPhoneDisplay(PhoneDisplay phoneDisplay) {
        this.phoneDisplay = phoneDisplay;
    }
    
    public SalePromotionInfor[] getSalePromotionInfors() {
        return salePromotionInfors;
    }
    public void setSalePromotionInfors(SalePromotionInfor[] salePromotionInfors) {
        this.salePromotionInfors = salePromotionInfors;
    }
//    @Override
//    public String toString() {
//        return "Prices [_id=" + _id + ", id=" + id + ", prices=" + Arrays.toString(prices) + ", phoneDisplay="
//                + phoneDisplay + "]";
//    }
    @Override
    public String toString() {
        return "Prices [_id=" + _id + ", id=" + id + ", prices=" + Arrays.toString(prices) + ", salePromotionInfors="
                + Arrays.toString(salePromotionInfors) + ", phoneDisplay=" + phoneDisplay + "]";
    }
    
    
    
    

}
