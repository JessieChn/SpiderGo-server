package com.example.demo.entity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//内嵌文档不用写集合名，加上@Document就行了
@Document
public class Shop {
//    private String name;
//    private String text;
    @Field("shop_name")
    private String shopName;
    @Field("shop_url")
    private String shopUrl;
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopUrl() {
        return shopUrl;
    }
    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }
    @Override
    public String toString() {
        return "Shop [shopName=" + shopName + ", shopUrl=" + shopUrl + "]";
    }
    
}
