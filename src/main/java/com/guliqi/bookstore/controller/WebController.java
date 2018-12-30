package com.guliqi.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }

    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/account")
    public String account(@RequestParam String user_id){
        return "account";
    }
}
