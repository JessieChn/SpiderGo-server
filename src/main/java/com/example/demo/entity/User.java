package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String phoneNumber;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    @OrderBy(value = "create_time desc")
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Collection> collections = new ArrayList<>();
    
    @OrderBy(value = "create_time desc")
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Trace> traces = new ArrayList<>();
    
    public void addColl(Collection collection) {
        collections.add(collection);
    }
    
    public void addTrace(Trace trace) {
        traces.removeIf(t -> t.getPhoneId().equals(trace.getPhoneId()));
        traces.add(trace);
    }
    
    public User() {
        
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public List<Collection> getCollections() {
        return collections;
    }
    
    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }
    
    
    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", phoneNumber=" + phoneNumber + ", password=" + password + ", createTime="
                + createTime + "]";
    }
    
    
    
    
    
}
