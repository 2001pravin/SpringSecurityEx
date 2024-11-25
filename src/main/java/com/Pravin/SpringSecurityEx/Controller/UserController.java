package com.Pravin.SpringSecurityEx.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Pravin.SpringSecurityEx.Entity.User;
import com.Pravin.SpringSecurityEx.Services.IUserServices;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class UserController {

      private static final Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Autowired
  private  IUserServices iUserServices;

@PostMapping("/register")
public User register(@RequestBody User user){
  
    return iUserServices.register(user) ;

    }

@PostMapping("/login")
    public String login(@RequestBody User user){
      System.out.println("request is come");
      logger.info("User login request: {}", user);
      System.out.println(user);
      return iUserServices.verify(user);
    }
}
