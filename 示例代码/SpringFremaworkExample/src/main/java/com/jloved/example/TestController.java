package com.jloved.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        System.out.println("===== TestController index");
        return "index.html";
    }
}

