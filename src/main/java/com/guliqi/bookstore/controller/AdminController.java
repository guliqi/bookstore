package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.model.Admin;
import com.guliqi.bookstore.service.AdminService;
import com.guliqi.bookstore.service.RedisService;
import com.guliqi.bookstore.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private TokenService tokenService;
    private RedisService redisService;

    @Autowired
    public AdminController(AdminService adminService, TokenService tokenService, RedisService redisService){
        this.adminService = adminService;
        this.tokenService = tokenService;
        this.redisService = redisService;
    }

    @ApiOperation("测试管理员登录")
    @PostMapping("/token")
    public JSONObject login(@RequestBody Admin admin){
        JSONObject jsonObject = adminService.login(admin);
        if (jsonObject.get("message") == "success"){
            String token = tokenService.getToken(admin);
            jsonObject.put("token", token);
        }
        return jsonObject;
    }
}
