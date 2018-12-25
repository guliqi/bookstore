package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.interceptor.OnlyAdmin;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.model.Address;
import com.guliqi.bookstore.model.Application;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.service.ApplicationService;
import com.guliqi.bookstore.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {
    private ApplicationService applicationService;
    private TokenService tokenService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, TokenService tokenService){
        this.applicationService = applicationService;
        this.tokenService = tokenService;
    }

    @UserLoginToken
    @ApiOperation(value = "测试申请开店")
    @PostMapping(value = "/new")
    public JSONObject apply(@RequestHeader String token,
                            @RequestParam String storename, @RequestBody Map<String, String> map){
        String user_id = tokenService.getIdOrName(token);
        String address_id = map.get("address_id");
        String bank_card = map.get("bank_card");
        String introduction = map.get("introduction");
        Application application = new Application.Builder().user(new User(user_id))
                .storename(storename).address(new Address(address_id)).bank_card(bank_card)
                .introduction(introduction).build();
        return applicationService.applyForStore(application);
    }


    @ApiOperation(value = "测试审核申请")
    @PutMapping(value = "/{application_id}")
    @UserLoginToken
    @OnlyAdmin
    public JSONObject check(@RequestHeader String token,
                            @PathVariable String application_id, @RequestParam String state){
        String admin = tokenService.getIdOrName(token);
        Application application = new Application.Builder().application_id(application_id)
                .state(state).admin(admin).build();
        return applicationService.checkApplication(application);
    }
}
