package com.example.demo.entity;


public class FilterParam {
    private String brand;
    private String ram;
    private String rom;
    private String keyword;
    private String hotSpot;
    public String getBrand() {
        if(brand == null)
            return "";
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getRam() {
        if(ram == null)
            return "";
        return ram;
    }
    public void setRam(String ram) {
        this.ram = ram;
    }
    public String getRom() {
        if(rom == null)
            return "";
        return rom;
    }
    public void setRom(String rom) {
        this.rom = rom;
    }
    public String getKeyword() {
        if(keyword == null)
            return "";
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String getHotSpot() {
        if(hotSpot == null)
            return "";
        return hotSpot;
    }
    public void setHotSpot(String hotSpot) {
        this.hotSpot = hotSpot;
    }
    @Override
    public String toString() {
        return "FilterParam [brand=" + brand + ", ram=" + ram + ", rom=" + rom + ", keyword=" + keyword + ", hotSpot="
                + hotSpot + "]";
    }
    
    
}
