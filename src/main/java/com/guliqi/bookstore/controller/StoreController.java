package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.service.StoreService;
import com.guliqi.bookstore.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
public class StoreController {
    private StoreService storeService;
    private TokenService tokenService;

    @Autowired
    public StoreController(StoreService storeService, TokenService tokenService){
        this.storeService = storeService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "测试打开商店")
    @GetMapping(value = "/{store_id}")
    public JSONObject enterStore(@PathVariable String store_id){
        return storeService.enterStore(store_id);
    }

    @ApiOperation(value = "测试查看我的商店")
    @GetMapping(value = "/my_store")
    @UserLoginToken
    public JSONObject checkOwnStore(@RequestHeader String token){
        String user_id = tokenService.getIdOrName(token);
        return storeService.checkOwnStores(user_id);
    }
}
