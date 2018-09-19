package com.example.demo.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//这里对应的数据库集合名为movie，如果不填，默认为小写movie
@Document(collection="movie")
public class Movie {
    
    @Id
    private String id;
    private String title;
    private Integer like;
    private Integer dislike;
    private String[] author;
    @Field("comment2")
    private List<Comment> comment;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getLike() {
        return like;
    }
    public void setLike(Integer like) {
        this.like = like;
    }
    public Integer getDislike() {
        return dislike;
    }
    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
    public String[] getAuthor() {
        return author;
    }
    public void setAuthor(String[] author) {
        this.author = author;
    }
    public List<Comment> getComment() {
        return comment;
    }
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", like=" + like + ", dislike=" + dislike + ", author="
                + Arrays.toString(author) + ", comment=" + comment + "]";
    }
    
      
}
