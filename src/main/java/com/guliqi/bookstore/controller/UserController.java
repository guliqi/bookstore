package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.service.RedisService;
import com.guliqi.bookstore.service.TokenService;
import com.guliqi.bookstore.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private TokenService tokenService;
    private RedisService redisService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService, RedisService redisService){
        this.userService = userService;
        this.tokenService = tokenService;
        this.redisService = redisService;
    }

    @ApiOperation(value = "测试用户注册")
    @JsonView(User.UserDetailView.class)
    @PostMapping(value = "")
    public JSONObject register(@RequestBody User user){
        JSONObject jsonObject = userService.register(user);
        if (jsonObject.get("message") == "success"){
            System.out.println(user.getUser_id());
            String token = tokenService.getToken(user);
            jsonObject.put("token", token);
        }
        return jsonObject;
    }

    @ApiOperation(value = "测试用户登录")
    @JsonView(User.UserSimpleView.class)
    @PostMapping(value = "/token")
    public JSONObject login(@RequestBody Map<String, String> map){
        User user = new User(map.get("phone"), map.get("password"));
        JSONObject jsonObject = userService.login(user);
        if (jsonObject.get("message") == "success"){
            String token = tokenService.getToken(user);
            jsonObject.put("token", token);
        }
        return jsonObject;
    }

    @ApiOperation(value = "测试用户登出")
    @UserLoginToken
    @DeleteMapping("/token")
    public JSONObject logout(@RequestHeader String token){
        JSONObject jsonObject = new JSONObject();
        redisService.set(token, new Date().toString(), 1L);
        jsonObject.put("message", "success");
        return jsonObject;
    }
}
