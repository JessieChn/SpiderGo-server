package com.example.demo.entity;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="opinions")
public class Opinions {
    @Id
    private String _id;
    private String id;
    @Field("opinion_value")
    private OpinionValue[] opinionValues;
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
    public OpinionValue[] getOpinionValues() {
        return opinionValues;
    }
    public void setOpinionValues(OpinionValue[] opinionValues) {
        this.opinionValues = opinionValues;
    }
    @Override
    public String toString() {
        return "Opinions [_id=" + _id + ", id=" + id + ", opinionValues=" + Arrays.toString(opinionValues) + "]";
    }
}