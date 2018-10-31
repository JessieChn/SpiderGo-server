package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
    //User findByUsernameAndPassword(String username, String password);
    
    User findByPhoneNumberAndPassword(String phoneNumber, String password);
    
    User findByPhoneNumber(String phoneNumber);

}