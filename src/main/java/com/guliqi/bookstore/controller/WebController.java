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

    @GetMapping(value = "/store")
    public String store(@RequestParam String store_id){
        return "store";
    }

    @GetMapping(value = "/book")
    public String book(@RequestParam String book_id){
        return "book";
    }

    @GetMapping(value = "/orders")
    public String orders(@RequestParam String user_id){
        return "orders";
    }

    @GetMapping(value = "/store_orders")
    public String storeOrders(@RequestParam String store_id){
        return "storeOrders";
    }

    @GetMapping(value = "/search")
    public String search(@RequestParam String bookname){
        return "search";
    }

    @GetMapping(value = "/test")
    public String test(){
        return "test";
    }
}
