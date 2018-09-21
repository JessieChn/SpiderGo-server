package com.example.demo.entity;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.error.ShouldBeAbsolutePath;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//这里对应的数据库集合名为movie，如果不填，默认为小写movie
@Document(collection="phone")
public class Phone {
    @Id
    private String _id;
//    private String title;
//    private Integer like;
//    private Integer dislike;
//    private String[] author;
//    private List<Comment> comment;
    private String id;
    private String name;
    private String url;
    private String source;
    private Shop shop;
    @Field("thumb_pic")
    private String[] thumbPic;
    @Field("detail_url")
    private String[] detailUrl;
    @Field("video_url")
    private String videoUrl;
    @Field("hot_spot")
    private String[] hotSpot;
    @Field("r_camera")
    private String rCamera;
    @Field("f_camera")
    private String fCamera;
    @Field("screen_size")
    private String screenSize;
    private String battery;
    @Field("pack_list")
    private String packList;
    private String brand;
    private String model;
    private String ram;
    private String rom;
    private String cpu;
    @Field("memory_card")
    private String memoryCard;
    @Field("max_mem_sup")
    private String maxMemSup;
    private String resolution;
    private String figer;
    private String newPrice;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public String[] getThumbPic() {
        return thumbPic;
    }
    public void setThumbPic(String[] thumbPic) {
        this.thumbPic = thumbPic;
    }
    public String[] getDetailUrl() {
        return detailUrl;
    }
    public void setDetailUrl(String[] detailUrl) {
        this.detailUrl = detailUrl;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String[] getHotSpot() {
        return hotSpot;
    }
    public void setHotSpot(String[] hotSpot) {
        this.hotSpot = hotSpot;
    }
    public String getrCamera() {
        return rCamera;
    }
    public void setrCamera(String rCamera) {
        this.rCamera = rCamera;
    }
    public String getfCamera() {
        return fCamera;
    }
    public void setfCamera(String fCamera) {
        this.fCamera = fCamera;
    }
    public String getScreenSize() {
        return screenSize;
    }
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }
    public String getBattery() {
        return battery;
    }
    public void setBattery(String battery) {
        this.battery = battery;
    }
    public String getPackList() {
        return packList;
    }
    public void setPackList(String packList) {
        this.packList = packList;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
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
    public String getCpu() {
        return cpu;
    }
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
    public String getMemoryCard() {
        return memoryCard;
    }
    public void setMemoryCard(String memoryCard) {
        this.memoryCard = memoryCard;
    }
    public String getMaxMemSup() {
        return maxMemSup;
    }
    public void setMaxMemSup(String maxMemSup) {
        this.maxMemSup = maxMemSup;
    }
    public String getResolution() {
        return resolution;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public String getFiger() {
        return figer;
    }
    public void setFiger(String figer) {
        this.figer = figer;
    }
    @Override
    public String toString() {
        return "Phone [_id=" + _id + ", id=" + id + ", name=" + name + ", url=" + url + ", source=" + source + ", shop="
                + shop + ", thumbPic=" + Arrays.toString(thumbPic) + ", detailUrl=" + Arrays.toString(detailUrl)
                + ", videoUrl=" + videoUrl + ", hotSpot=" + Arrays.toString(hotSpot) + ", rCamera=" + rCamera
                + ", fCamera=" + fCamera + ", screenSize=" + screenSize + ", battery=" + battery + ", packList="
                + packList + ", brand=" + brand + ", model=" + model + ", ram=" + ram + ", rom=" + rom + ", cpu=" + cpu
                + ", memoryCard=" + memoryCard + ", maxMemSup=" + maxMemSup + ", resolution=" + resolution + ", figer="
                + figer + "]";
    }
    

}
