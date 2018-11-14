package com.example.demo.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //public java.lang.String com.example.demo.controller.phoneController.listCollInformation
        //(org.springframework.ui.Model,javax.servlet.http.HttpSession)
        System.out.println(handler);
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/toLogin");
            return false;
        }
        return true;
    }
}
