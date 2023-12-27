package com.jloved.example.service.impl;

import com.jloved.example.bean.User;
import com.jloved.example.service.UserService;
import com.jloved.example.springmvc.annotation.Service;

/**
 * @author 白起老师
 */
@Service(value="userService")
public class UserServiceImpl implements UserService {


    public  void  findUser(){
        System.out.println("====调用UserServiceImpl==findUser===");
    }

    public User getUser(){

       return new User(1,"老王","admin");
    }

}
