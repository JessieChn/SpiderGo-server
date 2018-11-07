package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Topic {

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;

    @ManyToMany
    private List<Article> articles = new ArrayList<>();

    public void addArticle(Article article) {
        articles.add(article);
    }
    
    public Topic() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    @JsonBackReference
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    
    
    @Override
    public String toString() {
        return "Topic [id=" + id + ", name=" + name + "]";
    }

    
}
