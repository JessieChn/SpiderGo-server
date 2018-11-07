package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Comment2;

public interface Comment2Repository extends JpaRepository<Comment2,Long>{

}
