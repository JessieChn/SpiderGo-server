package com.example.demo.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String nickName;
    private String phone;
    @Temporal(TemporalType.DATE)
    private Date signDate;
    
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE}
                ,optional = false
                        ,fetch = FetchType.EAGER) //设置为懒加载在网页中会报错
    @JoinColumn(name = "author_wallet_id")
    private Wallet wallet;
    
    public Author() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", nickName=" + nickName + ", phone=" + phone + ", signDate=" + signDate
                + ", wallet=" + wallet + "]";
    }


    

    
    
}
