package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.StoreMapper;
import com.guliqi.bookstore.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private StoreMapper storeMapper;

    @Autowired
    public StoreService(StoreMapper storeMapper){
        this.storeMapper = storeMapper;
    }

    public JSONObject enterStore(String store_id){
        JSONObject jsonObject = new JSONObject();
        Store store = storeMapper.selectById(store_id);
        if (store == null)
            jsonObject.put("message", "store does not exist");
        else {
            jsonObject.put("store", store);
            jsonObject.put("message", "success");
        }
        return jsonObject;
    }
}
