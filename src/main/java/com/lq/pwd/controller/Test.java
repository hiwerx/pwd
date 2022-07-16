package com.lq.pwd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Test {

    @RequestMapping("hehe")
    public Object oo(){
        return "hehe";
    }
}
