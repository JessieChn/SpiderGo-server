package com.example.demo.controller;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Phone;
import com.example.demo.entity.RndScope;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.example.demo.repository.PhoneRepository;;

@RestController
public class PhoneController {
    
    @Autowired
    private PhoneRepository phoneRepository;
    
    private final static DBObject listField = new BasicDBObject(){{
       put("id", true); 
       put("name", true); 
       put("source", true);
       put("ram", true);
       put("rom", true);
       DBObject sliceObj = new BasicDBObject();
       sliceObj.put("$slice", 1);
       put("thumb_pic", sliceObj); 
    }};
    
    @RequestMapping("/still")
    public Object phoneList3() {
        //获得mongo operations
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        DBObject dbObject = new BasicDBObject();
        //dbObject.put("", v)
        Query query = new BasicQuery(dbObject, listField).limit(20).skip(0);
        List<Phone> phones = mongoOps.find(query,Phone.class);
        for(Phone phone : phones) {
            System.out.println(phone);
        }
        return phones;
    }
    
    @RequestMapping("/say")
    public Object phoneList() {
      //获得mongo operations
      MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");

      //dbObject
      DBObject dbObject = new BasicDBObject();
      //dbObject.put("id", "jd7357933");
      DBObject fieldObject2 = new BasicDBObject();
      fieldObject2.put("$slice", 1);//

      DBObject fieldObject = new BasicDBObject();
      
      fieldObject.put("id", true);
      fieldObject.put("name", true);
      //fieldObject.put("url", true);
      fieldObject.put("thumbPic", fieldObject2);//
      fieldObject.put("hot_spot", true);
      fieldObject.put("video_url", true);
      fieldObject.put("detail_url", true);
      Query query = new BasicQuery(dbObject, fieldObject).limit(20).skip(0);
      
      List<Phone> phones = mongoOps.find(query,Phone.class);
      for(Phone phone : phones) {
          System.out.println(phone);
      }
      return phones;
    }
    
    @RequestMapping("/hello")  //regex 模糊匹配
    public Object phoneList2() {
        //phoneRepository.findAll(arg0)
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        List<Phone> phones = mongoOps.find(query(where("brand").regex("小米").and("model").regex("红米note5","i")),Phone.class);//加上i可以保证不区分大小写
        for(Phone phone : phones) {
            System.out.println(phone);
        }
        return phones;
        
    }
    
    /***
     * 从数据集中，随机得到指定个数的文档。
     * @param nLimit 随机得到文档个数
     * @param query 先决条件
     * @param dbCollection 数据集
     * @return
     * @throws Exception
     */
    public DBCursor GetRandomResult(int nLimit,BasicDBObject query,DBCollection dbCollection)
            throws Exception{
        if(query==null){
            query=new BasicDBObject();
        }
        if(query.containsKey("$where")){
            throw new Exception("查询中不能包含$where项");
        }
        
        //通过ObjectId.get()得到二个唯一的变量名。
        String sgteArgs="_OpenTmp"+ObjectId.get().toString();
        String slteArgs="_OpenTmp"+ObjectId.get().toString();
        System.out.println(sgteArgs);
        System.out.println(slteArgs);
        
        //得到数据的总数
        long nLength=dbCollection.count(query);
        System.out.println("记录总数:"+nLength);
        //根据要获取的记录的条数，对范围值进行修改
        RndScope rndScope=new RndScope(nLength,nLimit);

        //将创建时间太久的给删除掉
        DBCollection dbSystem=dbCollection.getDB().getCollection("system.js");
        dbSystem.remove(new BasicDBObjectBuilder()
            .add("createDate",
                    new BasicDBObjectBuilder()
                    .add("$lt",new Date((new Date()).getTime()-24*60*1000)).get())
            .add("_id",Pattern.compile("_OpenTmp*"))
            .get()
                );
        //向system.js集合中添加二个全局的变量
        dbSystem.findAndModify(
                new BasicDBObjectBuilder().add("_id", sgteArgs).get(),
                null,null, false, new BasicDBObjectBuilder()
                .add("_id", sgteArgs)
                .add("value",rndScope.getGteVal())
                .add("createDate",new Date())
                .get(), false, true);
        dbSystem.findAndModify(new BasicDBObjectBuilder().add("_id",slteArgs).get()
                ,null,null,false,new BasicDBObjectBuilder()
                                    .add("_id",slteArgs)
                                    .add("value",rndScope.getLteVal())
                                    .add("createDate",new Date())
                                    .get(),false,true);
        
        //查询中添加对于随机数的判断
        query.put("$where", "function(){var rnd=Math.random();return (rnd>="
                    +sgteArgs+" && rnd<="+slteArgs+");}");
        
        DBCursor result=dbCollection.find(query).limit(nLimit);
        
        return result;
    }
    
    @RequestMapping("/nothing")
    public Object phoneList4() throws Exception {
        //获得mongo operations
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        DBCollection collection = mongoOps.getCollection("phone");
        DBObject dbObject = new BasicDBObject();
        //dbObject.put("", v)
        Query query = new BasicQuery(dbObject, listField).limit(5).skip(0);
/*        List<Phone> phones = mongoOps.find(query,Phone.class);
        for(Phone phone : phones) {
            System.out.println(phone);
        }*/
        DBCursor dbCursor = GetRandomResult(5,new BasicDBObject(),collection);
        System.out.println(dbCursor.count());
        while(dbCursor.hasNext()) {
            DBObject obj = dbCursor.next();
            System.out.println(obj);
         }
        return null;
    }

}