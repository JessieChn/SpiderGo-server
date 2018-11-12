package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "t_trace")
public class Trace {
    @Id
    @GeneratedValue
    private Long id;
    private String phoneId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToMany
    private List<User> users = new ArrayList<>();
    
    public List<User> getUsers() {
        return users;
    }
    
    @JsonBackReference
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Trace() {
        
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
        return "Trace [id=" + id + ", phoneId=" + phoneId + ", createTime=" + createTime + "]";
    }
}
