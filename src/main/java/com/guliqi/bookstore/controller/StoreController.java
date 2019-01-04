package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
import com.guliqi.bookstore.model.Views;
import com.guliqi.bookstore.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/request/store")
public class StoreController {
    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @ApiOperation(value = "测试打开商店")
    @GetMapping(value = "")
    @JsonView(Views.WithUserView.class)
    public JSONObject enterStore(@RequestParam String store_id){
        return storeService.enterStore(store_id);
    }

}
