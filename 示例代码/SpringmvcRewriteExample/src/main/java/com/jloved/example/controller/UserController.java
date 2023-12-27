package com.jloved.example.controller;

import com.jloved.example.bean.User;
import com.jloved.example.service.UserService;
import com.jloved.example.springmvc.annotation.AutoWired;
import com.jloved.example.springmvc.annotation.Controller;
import com.jloved.example.springmvc.annotation.RequestMapping;
import com.jloved.example.springmvc.annotation.ResponseBody;

/**
 * @author 白起老师
 */
@Controller
public class UserController {

       @AutoWired(value="userService")
       private UserService userService;


       //定义方法
       @RequestMapping("/findUser")
       public  String  findUser(String name){
           //调用服务层
           userService.findUser();
           return "forward:/success.jsp";
       }

    @RequestMapping("/getData")
    @ResponseBody  //返回json格式的数据
    public User getData(){
        //调用服务层
        return userService.getUser();
    }
}
