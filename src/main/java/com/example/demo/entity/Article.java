package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    private String content;
    

    @OneToMany(mappedBy = "article" , cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<Comment2> comment2s = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Topic> topics = new ArrayList<>();
    
    public void addTopic(Topic topic) {
        topics.add(topic);
    }
    
    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public void addComment2(Comment2 comment2) {
        comment2.setArticle(this);
        comment2s.add(comment2);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment2> getComment2s() {
        return comment2s;
    }

    public void setComment2s(List<Comment2> comment2s) {
        this.comment2s = comment2s;
    }

    @Override //list不在toString中显示，不然会 java.lang.StackOverflowError: null
    public String toString() {
        return "Article [id=" + id + ", title=" + title + ", content=" + content + "]";
    }
    
    
}
