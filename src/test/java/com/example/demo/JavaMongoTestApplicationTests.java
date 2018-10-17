package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import com.example.demo.entity.Director;
import com.example.demo.entity.Movie;
import com.example.demo.entity.Phone;
import com.example.demo.entity.Prices;
import com.example.demo.entity.RndScope;
import com.example.demo.entity.User;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.BeanUtil;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import net.minidev.json.writer.BeansMapper.Bean;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
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
/*        Page<Phone> phonePage = phoneRepository.findByBrand("小辣椒", new PageRequest(0, 5));
        System.out.println(phonePage);*/
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
    
    @Test
    public void aggregateFunction(){
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
/*        new newAggregation(
                
                ) */
        //mongoOps.aggregate(aggregation, inputType, outputType)
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
    
    @Test
    public void DocumentTest5() throws Exception{
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        DBCollection collection = mongoOps.getCollection("phone");
        System.out.println(collection.getCount());
        DBCursor dbCursor = GetRandomResult(20,new BasicDBObject(),collection);
        System.out.println(dbCursor.count());
        while(dbCursor.hasNext()) {
            DBObject obj = dbCursor.next();
            System.out.println(obj);
         }
    }
    //注意这个test8 拥有翻页，过滤，project,slice等功能。
    @Test
    public void DocumentTest8() throws Exception{
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        Query query = new Query(Criteria.where("brand").regex("小米").and("ram").in("6GB","4GB"));//还支持shop.shop_name，不支持数组操作
        query.fields().include("name");
        query.fields().include("ram");
        query.fields().include("detail_url");
        query.fields().include("shop.shop_name"); //还支持shop.shop_name ，不支持数组操作
        query.fields().slice("thumb_pic", 1);
        
        Pageable pageable = new PageRequest(0, 5); //页数是从0开始
        query.with(pageable);
        List<Phone> phones = mongoOps.find(query,Phone.class);
        for(Phone phone : phones) {
            System.out.println(phone);
        }
        /*Query query = new Query();
        query.addCriteria(Criteria.where("_id").gt(new ObjectId(tsdataId)));
        query.fields().include("_id");
        query.fields().include("cust.number");
        query.with(new Sort(new Order(Direction.ASC, "_id")));
        query.limit(pageSize);*/
        
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
    
    @Test
    public void PhoneDocumentTest6(){
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");
        MatchOperation  match3 = Aggregation.match(new Criteria("_id").is("5b88e5d400199531c0b4093e"));
        TypedAggregation<Phone> agg = Aggregation.newAggregation(Phone.class,
                
                match(Criteria.where("brand").is("小辣椒")),
                
                project("name","id"),match3);
       
/*        DBObject sample = new BasicDBObject("$sample", new BasicDBObject("size", 5));
        AggregationOutput output = mongoOps.getCollection("jd").aggregate(sample);
        Iterable<DBObject> list2 = output.results();
        for (DBObject dbObject : list2) {
            System.out.println(dbObject);
        }版本有问题  
        */
        
        //AggregationResults<String> groupResults = mongoOps.aggregate(agg, String.class);
        //System.out.println(groupResults.getMappedResults());
        AggregationResults<Phone> list = mongoOps.aggregate(agg, Phone.class);
        for(Phone phone : list) {
            System.out.println(phone.toString());
        }
        
    }
    
    @Test
    public void PhoneDocumentTest7(){
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd");        
        TypedAggregation<Phone> agg = Aggregation.newAggregation(Phone.class,
                //match(Criteria.where("shop.shop_name").regex("HTC")),
                match(Criteria.where("thumb_pic").regex("//img10.360buyimg.com/n5/s54x54_jfs/t5695/91/3561869215/323052/765e493f/593e0ff5N1e3493e5.jpg")),
                project("id","videoUrl","thumbPic"));
        AggregationResults<String> list = mongoOps.aggregate(agg, String.class);
        for(String phone : list) {
            System.out.println(phone);
        }
        
    }
    
    @Test
    public void PhoneDocumentTest8(){
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "jd3");   
        TypedAggregation<Prices> agg = Aggregation.newAggregation(
                Prices.class,
//                match(Criteria.where("prices").elemMatch(Criteria.where("price_value").regex("2097").and("date").is("2018-09-07"))),
                match(Criteria.where("prices").elemMatch(Criteria.where("date").regex("2018-09-27"))),
//                group(fields),
                sort(Sort.Direction.ASC,"id"),
                project("id","prices"),
//                skip(elementsToSkip),
//                s
               limit(10)
                );
        AggregationResults<String> list = mongoOps.aggregate(agg, String.class);
        for(String phone : list) {
            System.out.println(phone);
        }
        Phone phone = phoneRepository.findOne("jd5438529");
        System.out.println(phone);
//        List<Phone> phones =  phoneRepository.findByBrandAndRamAndRom(null, "6GB", "128GB");
//        for(Phone phone3 : phones) {
//            System.out.println("-----------------");
//            System.out.println(phone3);
//        }
        //phoneRepository.
    }
    @Test
    public void PhoneDocumentTest9(){
        List<Movie> movies = movieRepository.findAll();
        for(Movie movie: movies)
            System.out.println(movie);
    }
}

