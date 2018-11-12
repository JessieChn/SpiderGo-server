package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Trace;

public interface TraceRepository extends JpaRepository<Trace,Long>{

}
