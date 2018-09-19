package com.example.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

//内嵌文档不用写集合名，加上@Document就行了
@Document
public class Comment {
    private String name;
    private String text;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return "Comment [name=" + name + ", text=" + text + "]";
    }
}