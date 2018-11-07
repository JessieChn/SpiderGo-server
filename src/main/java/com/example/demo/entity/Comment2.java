package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment2 {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    
    @ManyToOne
    private Article article;

    public void clearComment() {
        this.article.getComment2s().remove(this);
    }
    
    public Comment2() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Article getArticle() {
        return article;
    }

    @JsonBackReference
    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Comment2 [id=" + id + ", content=" + content + ", article=" + article + "]";
    }

    
    
}
