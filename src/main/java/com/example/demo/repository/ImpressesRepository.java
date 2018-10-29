package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entity.Impresses;

public interface ImpressesRepository extends MongoRepository<Impresses,String>{
    @Query(value = "{'_id':?0}", fields = "{'impress_value':{'$slice':-1}}")
    Impresses findByIdUnity(String id);
}
