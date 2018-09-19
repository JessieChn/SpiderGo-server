package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Phone;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.example.demo.repository.PhoneRepository;;

@RestController
public class PhoneList {
    
    @Autowired
    private PhoneRepository phoneRepository;
    
    @RequestMapping("/say")
    public Object phoneList() {
      MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");

      //返回指定的字段
      DBObject dbObject = new BasicDBObject();
      dbObject.put("id", "jd7357933");
      DBObject fieldObject2 = new BasicDBObject();
      fieldObject2.put("$slice", 1);

      DBObject fieldObject = new BasicDBObject();
      
      fieldObject.put("id", true);
      fieldObject.put("name", true);
      fieldObject.put("url", true);
      fieldObject.put("thumbPic", fieldObject2);
      fieldObject.put("hot_spot", true);
      fieldObject.put("video_url", true);
      fieldObject.put("detail_url", true);
      Query query = new BasicQuery(dbObject, fieldObject);
      List<Phone> phones = mongoOps.find(query,Phone.class);
      for(Phone phone : phones) {
          System.out.println(phone.getName());
          System.out.println(phone.getUrl());
          System.out.println(Arrays.toString(phone.getThumbPic()));
          System.out.println(Arrays.toString(phone.getHotSpot()));
          System.out.println(phone);
      }
      return phones;
    }

}