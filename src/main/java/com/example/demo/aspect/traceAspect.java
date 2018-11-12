package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.entity.Trace;
import com.example.demo.entity.User;
import com.example.demo.repository.TraceRepository;
import com.example.demo.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Date;


@Aspect
@Component
public class traceAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TraceRepository traceRepositorty;

    @Pointcut("execution(* com.example.demo.controller.phoneController.listInformation3(..))")
    public void log() {
    }


    @Before("log()")
    public void doBefore(JoinPoint joinPoint/*,HttpSession session*/) {
        //logger.info("--------doBefore--------");
        //logger.info("{}",joinPoint.getArgs());//获得参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            user = userRepository.findById(user.getId()).get();
            //logger.info("--user ------ {}--------",user);
            Trace trace = new Trace();
            trace.setPhoneId(joinPoint.getArgs()[0].toString());
            trace.setCreateTime(new Date());
            user.addTrace(trace);
            userRepository.save(user);
        }
    }

    @After("log()")
    public void doAfter() {
        //logger.info("--------doAfter--------");
    }
}
