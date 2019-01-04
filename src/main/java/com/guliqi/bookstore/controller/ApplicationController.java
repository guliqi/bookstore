package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
import com.guliqi.bookstore.interceptor.OnlyAdmin;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.model.Views;
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
    public JSONObject apply(@RequestHeader String token, @RequestBody Map<String, String> map){
        String user_id = tokenService.getIdOrName(token);
        String storename = map.get("storename");
        String address_id = map.get("address_id");
        String bank_card = map.get("bank_card");
        String introduction = map.get("introduction");
        if (storename == null || address_id == null || bank_card == null || introduction == null)
            throw new RuntimeException("incomplete information");
        return applicationService.applyForStore(user_id, storename, address_id, bank_card, introduction);
    }

    @ApiOperation(value = "测试审核申请")
    @PutMapping(value = "/{application_id}")
    @UserLoginToken
    @OnlyAdmin
    public JSONObject check(@RequestHeader String token,
                            @PathVariable String application_id, @RequestParam String state){
        String admin = tokenService.getIdOrName(token);
        return applicationService.checkApplication(admin, application_id, state);
    }

    @ApiOperation(value = "测试查看所有待审核申请")
    @GetMapping(value = "/applications")
    @UserLoginToken
    @JsonView(Views.WithUserView.class)
    @OnlyAdmin
    public JSONObject lookOver(@RequestHeader String token){
        return applicationService.lookOver();
    }
}
