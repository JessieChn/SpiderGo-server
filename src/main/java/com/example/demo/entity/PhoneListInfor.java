package com.example.demo.entity;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="phone")
public class PhoneListInfor {
    @Id
    private String _id;
    private String id;
    private String name;
    private String source;
    @Field("thumb_pic")
    private String[] thumbPic;
    private String ram;
    private String rom;
    private PriceInfor[] prices;
    private SalePromotionInfor[] salePromotionInfors;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String[] getThumbPic() {
        return thumbPic;
    }
    public void setThumbPic(String[] thumbPic) {
        this.thumbPic = thumbPic;
    }
    public String getRam() {
        return ram;
    }
    public void setRam(String ram) {
        this.ram = ram;
    }
    public String getRom() {
        return rom;
    }
    public void setRom(String rom) {
        this.rom = rom;
    }
    public PriceInfor[] getPrices() {
        return prices;
    }
    public void setPrices(PriceInfor[] prices) {
        this.prices = prices;
    }
    public SalePromotionInfor[] getSalePromotionInfors() {
        return salePromotionInfors;
    }
    public void setSalePromotionInfors(SalePromotionInfor[] salePromotionInfors) {
        this.salePromotionInfors = salePromotionInfors;
    }
    @Override
    public String toString() {
        return "PhoneListInfor [_id=" + _id + ", id=" + id + ", name=" + name + ", source=" + source + ", thumbPic="
                + Arrays.toString(thumbPic) + ", ram=" + ram + ", rom=" + rom + ", prices=" + Arrays.toString(prices)
                + ", salePromotionInfors=" + Arrays.toString(salePromotionInfors) + "]";
    }
    
    
    
    

}
