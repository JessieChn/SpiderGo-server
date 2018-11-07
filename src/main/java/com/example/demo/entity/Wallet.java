package com.example.demo.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;
    
    private BigDecimal balance;
    
//    @OneToOne(mappedBy = "wallet",fetch = FetchType.LAZY) //加了以后查询author to wallet会出现死循环,从 wallet到author不会
//    private Author author;
    
    public Wallet() {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }

    @Override
    public String toString() {
        return "Wallet [id=" + id + ", balance=" + balance + "]";
    }

    
    
    
    

}
