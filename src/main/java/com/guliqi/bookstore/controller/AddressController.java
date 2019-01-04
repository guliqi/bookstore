package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.model.Address;
import com.guliqi.bookstore.service.AddressService;
import com.guliqi.bookstore.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;
    private TokenService tokenService;

    @Autowired
    public AddressController(AddressService addressService, TokenService tokenService){
        this.addressService = addressService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "测试添加地址")
    @PostMapping("")
    @UserLoginToken
    public JSONObject addAddress(@RequestHeader String token, @RequestBody Address address){
        String user_id = tokenService.getIdOrName(token);
        return addressService.addAddress(address, user_id);
    }

    @ApiOperation(value = "测试修改地址")
    @PutMapping("")
    @UserLoginToken
    public JSONObject updateAddress(@RequestHeader String token, @RequestBody Address address){
        String user_id = tokenService.getIdOrName(token);
        return addressService.updateAddress(address, user_id);
    }

    @ApiOperation(value = "测试删除地址")
    @DeleteMapping("")
    @UserLoginToken
    public JSONObject deleteAddress(@RequestHeader String token, @RequestParam String address_id){
        String user_id = tokenService.getIdOrName(token);
        return addressService.removeAddress(address_id, user_id);
    }
}
