package com.example.demo.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entity.Opinions;
import com.example.demo.entity.Prices2;
public interface OpinionRepository extends MongoRepository<Opinions,String>{
    
    @Query(value = "{'_id':?0}", fields = "{'opinion_value':{'$slice':-1}}")
    Opinions findByIdUnity(String id);
    
    @Query(value = "{'_id':{$in:?0}}", fields = "{'opinion_value':{'$slice':-1}}")
    List<Opinions> findByIds(Iterable<String> ids);
}
