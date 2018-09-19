package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.Movie;
import com.example.demo.entity.Phone;
import com.example.demo.entity.User;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.BeanUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

import net.minidev.json.writer.BeansMapper.Bean;
import static org.springframework.data.mongodb.core.query.Criteria.where;
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaMongoTestApplicationTests {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private PhoneRepository phoneRepository;
    
    /**
     * 插入一条记录
     */
    @Test
    public void TestSave(){ 
        userRepository.insert(new User(1001L,"appleyk",27,"男"));
        System.err.println("保存成功！");
    }
    
    /**
     * 查询全部User实体
     */
    @Test
    public void TestFindAll(){  
        List<User> users = userRepository.findAll();
        System.err.println("size: "+users.size()+","+users.get(0).getName());
    }
    
    
    /**
     * 根据name查询User
     */
    @Test
    public void TestFindByName(){   
        List<User> users = userRepository.findByName("appleyk");
        System.err.println("size: "+users.size());
    }
    
    /**
     * 根据id删除对应User实体
     */
    @Test
    public void TestDelete(){
        userRepository.delete(1001L);
        System.err.println("删除成功！");
    }
    
    /**
     * bean2DBObject
     */
    @Test
    public void DBObjectProducter(){
        User user = new User(1001L,"appleyk",28,"男");
        try {
            DBObject dbObject = BeanUtil.bean2DBObject(user);
            System.out.println(dbObject);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 复杂嵌入文档测试
     */
    @Test
    public void DocumentTest(){
        List<Movie> movies =  movieRepository.findAll();
        System.err.println("size: "+movies.size()+","+movies.toString());
    }
    
    @Test
    public void DocumentTest2(){
        List<Movie> movies =  movieRepository.findDistinctTitleByTitle("羞羞的铁拳");
        System.err.println("size: "+movies.size()+","+movies.toString());
    }
    
    @Test
    public void DocumentTest3(){
        List<Phone> phones = phoneRepository.findByBrand("暂无信息");
        for(Phone phone : phones)
            System.out.println(phone);
    }
    
    @Test
    public void DocumentTest4(){
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");

        //返回指定的字段
        DBObject dbObject = new BasicDBObject();
        dbObject.put("id", "jd7357933");
        DBObject fieldObject2 = new BasicDBObject();
        fieldObject2.put("$slice", 1);

        DBObject fieldObject = new BasicDBObject();
        fieldObject.put("name", true);
        fieldObject.put("url", true);
        fieldObject.put("thumbPic", fieldObject2);
        fieldObject.put("hot_spot", true);
        Query query = new BasicQuery(dbObject, fieldObject);
        List<Phone> phones = mongoOps.find(query,Phone.class);
        for(Phone phone : phones) {
            System.out.println(phone.getName());
            System.out.println(phone.getUrl());
            System.out.println(Arrays.toString(phone.getThumbPic()));
            System.out.println(Arrays.toString(phone.getHotSpot()));
            System.out.println(phone);
        }
      }
    
    /**
     * 复杂嵌入文档测试
     */
    @Test
    public void PhoneDocumentTest(){
//        List<Movie> movies =  movieRepository.findAll();
//        System.err.println("size: "+movies.size()+","+movies.toString());
        List<Phone> phones = phoneRepository.findAll();
        //System.err.println("size: "+phones.size()+","+phones.toString());
        for(Phone phone : phones) {
            System.out.println(phone.getBattery());
        }
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        //System.out.println(mongoOps.findOne(new Query(where("id").is("jd7357933")), Phone.class));
        List distinct = mongoOps.getCollection("phone").distinct("hot_spot");
        System.out.println(distinct);
        
        //返回指定的字段
        DBObject dbObject = new BasicDBObject();
        dbObject.put("id", "jd7357933");
        DBObject fieldObject2 = new BasicDBObject();
        fieldObject2.put("$slice", 1);
        
        DBObject fieldObject = new BasicDBObject();
        fieldObject.put("name", true);
        fieldObject.put("url", true);
        fieldObject.put("thumbPic", fieldObject2);
        fieldObject.put("hot_spot", true);
        Query query = new BasicQuery(dbObject, fieldObject);
        //System.out.println(mongoOps.find(query,Phone.class));
        phones = mongoOps.find(query,Phone.class);
        for(Phone phone : phones) {
            System.out.println(phone.getName());
            System.out.println(phone.getUrl());
            System.out.println(Arrays.toString(phone.getThumbPic()));
            System.out.println(Arrays.toString(phone.getHotSpot()));
            System.out.println(phone);
        }
    }
    
    
    

}

