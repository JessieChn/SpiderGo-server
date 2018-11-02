package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "t_collection")
public class Collection {
    @Id
    @GeneratedValue
    private Long id;
    private String phoneId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    
    
    public Collection() {
        
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneId() {
        return phoneId;
    }
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Override
    public String toString() {
        return "Collection [id=" + id + ", phoneId=" + phoneId + ", createTime=" + createTime + "]";
    }


    

}
