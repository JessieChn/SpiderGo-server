package com.example.demo.entity;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="sale_promotions")
public class SalePromotion {
    @Id
    private String _id;
    private String id;
    @Field("sale_promotion")
    private SalePromotionInfor[] salePromotion;
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
    public SalePromotionInfor[] getSalePromotion() {
        return salePromotion;
    }
    public void setSalePromotion(SalePromotionInfor[] salePromotion) {
        this.salePromotion = salePromotion;
    }
    @Override
    public String toString() {
        return "SalePromotion [_id=" + _id + ", id=" + id + ", salePromotion=" + Arrays.toString(salePromotion) + "]";
    }
    
    

    
    
    
    
    

}
