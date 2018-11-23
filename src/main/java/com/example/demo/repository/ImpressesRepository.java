package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entity.Impresses;
import com.example.demo.entity.Prices2;

public interface ImpressesRepository extends MongoRepository<Impresses,String>{
    @Query(value = "{'_id':?0}", fields = "{'impress_value':{'$slice':-1}}")
    Impresses findByIdUnity(String id);
    
    @Query(value = "{'_id':{$in:?0}}", fields = "{'impress_value':{'$slice':-1}}")
    List<Impresses> findByIds(Iterable<String> ids);
}
