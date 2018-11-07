package com.example.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.Author;
import com.example.demo.entity.Phone;
import com.example.demo.entity.PhoneDisplay;
import com.example.demo.entity.Prices;
import com.example.demo.entity.SalePromotion;
import com.example.demo.entity.SalePromotionInfor;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.PhoneRepository;
import com.example.demo.repository.SalePromotionRepository;
import com.example.demo.repository.WalletRepository;
import com.mongodb.MongoClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot2XApplicationTests {

    @Resource
    private AuthorRepository authorRepository;
    
    @Resource
    private WalletRepository walletRepository;
    
    @Test
    public void contextLoads() {
        //System.out.println("~~~~~~");
        Author author = new Author();
        author.setNickName("池章立");
        author.setPhone("13538628500");
        author.setSignDate(new Date());
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(123));
        author.setWallet(wallet);
        authorRepository.save(author);
    }

    @Test
    public void contextLoads2() {
        List<Author> author = authorRepository.findAll();
        System.out.println("~~~~~~~~~~~~~~");
        System.out.println(author);
    }
}
