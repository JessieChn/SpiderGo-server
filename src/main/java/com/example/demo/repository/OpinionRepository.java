package com.example.demo.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entity.Opinions;
public interface OpinionRepository extends MongoRepository<Opinions,String>{
    
    @Query(value = "{'_id':?0}", fields = "{'opinion_value':{'$slice':-1}}")
    Opinions findByIdUnity(String id);
}
