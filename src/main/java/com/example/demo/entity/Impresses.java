package com.example.demo.entity;

import java.util.Arrays;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="impresses")
public class Impresses {
    @Id
    private String _id;
    private String id;
    @Field("impress_value")
    private Map<String, String>[] impressValue;
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
    public Map<String, String>[] getImpressValue() {
        return impressValue;
    }
    public void setImpressValue(Map<String, String>[] impressValue) {
        this.impressValue = impressValue;
    }
    @Override
    public String toString() {
        return "Impresses [_id=" + _id + ", id=" + id + ", impressValue=" + Arrays.toString(impressValue) + "]";
    }
    
    

}
