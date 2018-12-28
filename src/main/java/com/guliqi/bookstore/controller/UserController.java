package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
import com.guliqi.bookstore.Constants;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.model.Views;
import com.guliqi.bookstore.service.RedisService;
import com.guliqi.bookstore.service.TokenService;
import com.guliqi.bookstore.service.UserService;
import com.guliqi.bookstore.utils.CookieUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    @JsonView(Views.WithoutPasswordView.class)
    @PostMapping(value = "")
    public JSONObject register(@RequestBody User user, HttpServletResponse response){
        JSONObject jsonObject = userService.register(user);
        if (jsonObject.get("message") == "success"){
            String token = tokenService.getToken(user);
            Cookie cookie = CookieUtil.getTokenCookie(Constants.LOCALHOST, "/", token);
            response.addCookie(cookie);
        }
        return jsonObject;
    }

    @ApiOperation(value = "测试用户登录")
    @JsonView(Views.WithoutPasswordView.class)
    @PostMapping(value = "/token")
    public JSONObject login(@RequestBody Map<String, String> map, HttpServletResponse response){
        User user = new User(map.get("phone"), map.get("password"));
        JSONObject jsonObject = userService.login(user);
        if (jsonObject.get("message") == "success"){
            String token = tokenService.getToken(user);
            Cookie cookie = CookieUtil.getTokenCookie(Constants.LOCALHOST, "/", token);
            response.addCookie(cookie);
        }
        return jsonObject;
    }

    @ApiOperation(value = "测试检验token")
    @PostMapping(value = "/token/pass")
    @UserLoginToken
    public JSONObject pass(@RequestHeader String token, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Cookie cookie = CookieUtil.getTokenCookie(Constants.LOCALHOST, "/", token);
        response.addCookie(cookie);
        jsonObject.put("message", "success");
        return jsonObject;
    }

    @ApiOperation(value = "测试用户登出")
    @DeleteMapping("/token")
    @UserLoginToken
    public JSONObject logout(@RequestHeader String token){
        JSONObject jsonObject = new JSONObject();
        redisService.set(token, new Date().toString(), 1L);
        jsonObject.put("message", "success");
        return jsonObject;
    }

    @ApiOperation(value = "测试获取用户地址")
    @GetMapping("/addresses")
    @UserLoginToken
    @JsonView(Views.WithoutUserView.class)
    public JSONObject getAddresses(@RequestHeader String token){
        String user_id = tokenService.getIdOrName(token);
        return userService.getInformation(user_id, Constants.ADDRESS);
    }

    @ApiOperation(value = "测试获取用户申请")
    @GetMapping("/applications")
    @UserLoginToken
    @JsonView(Views.WithoutUserView.class)
    public JSONObject getApplications(@RequestHeader String token){
        String user_id = tokenService.getIdOrName(token);
        return userService.getInformation(user_id, Constants.APPLICATION);
    }

    @ApiOperation(value = "测试获取用户店铺")
    @GetMapping("/stores")
    @UserLoginToken
    @JsonView(Views.WithoutUserView.class)
    public JSONObject getStores(@RequestHeader String token){
        String user_id = tokenService.getIdOrName(token);
        return userService.getInformation(user_id, Constants.STORE);
    }
}
