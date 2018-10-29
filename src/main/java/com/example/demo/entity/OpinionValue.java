package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.StreamingHttpOutputMessage;

@Document
public class OpinionValue {
    @Field("high_number")
    private String highNumber;
    
    @Field("medium_number")
    private String mediumNumber;
    
    @Field("low_number")
    private String lowNumber;
    
    private String score;
    private String date;
    
    
    public String getHighNumber() {
        return highNumber;
    }

    public void setHighNumber(String highNumber) {
        this.highNumber = highNumber;
    }

    public String getMediumNumber() {
        return mediumNumber;
    }

    public void setMediumNumber(String mediumNumber) {
        this.mediumNumber = mediumNumber;
    }

    public String getLowNumber() {
        return lowNumber;
    }

    public void setLowNumber(String lowNumber) {
        this.lowNumber = lowNumber;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OpnionValue [highNumber=" + highNumber + ", mediumNumber=" + mediumNumber + ", lowNumber=" + lowNumber
                + ", score=" + score + ", date=" + date + "]";
    }
    
    
}
   