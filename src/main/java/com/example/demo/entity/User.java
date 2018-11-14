package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
//    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$",message = "手机号必须匹配")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机号必须匹配") //注意java和javascript的正则有不同，去掉头和尾的一些符号就可以了。
    private String phoneNumber;
    
    @NotNull(message = "密码不能为空")
    @Size(min = 6, message = "密码最小长度为6")
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
