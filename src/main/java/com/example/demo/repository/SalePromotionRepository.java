package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.Nullable;

import com.example.demo.entity.Phone;
import com.example.demo.entity.PhoneDisplay;
import com.example.demo.entity.Prices2;
import com.example.demo.entity.SalePromotion;
import com.example.demo.entity.SalePromotionInfor;

import java.lang.String;
public interface SalePromotionRepository extends MongoRepository<SalePromotion,String>{
    /*
     * MongoRepository与HibernateTemplete相似，提供一些基本的方法，
     * 实现的方法有findone(),save(),count(),findAll(),findAll(Pageable),delete(),deleteAll()..etc
     * 要使用Repository的功能，先继承MongoRepository<T, TD>接口
     * 其中T为仓库保存的bean类，TD为该bean的唯一标识的类型，一般为ObjectId。
     * 之后在spring-boot中注入该接口就可以使用，无需实现里面的方法，spring会根据定义的规则自动生成。
     * starter-data-mongodb 支持方法命名约定查询 findBy{User的name属性名}，
     * findBy后面的属性名一定要在User类中存在，否则会报错 
     */
    
    
    
    @Query(value = "{'id':?0}", fields = "{'sale_promotion':{'$slice':-1}}")
    SalePromotion findByIdUnity(String id);
    
    @Query(value = "{'_id':{$in:?0}}", fields = "{'sale_promotion':{'$slice':-1}}")
    List<SalePromotion> findByIds(Iterable<String> ids);
    
    
}
