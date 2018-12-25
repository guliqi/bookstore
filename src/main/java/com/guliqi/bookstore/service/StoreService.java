package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.StoreMapper;
import com.guliqi.bookstore.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StoreService {
    private StoreMapper storeMapper;

    @Autowired
    public StoreService(StoreMapper storeMapper){
        this.storeMapper = storeMapper;
    }

    public JSONObject checkOwnStores(String user_id){
        JSONObject jsonObject = new JSONObject();
        Set<Store> stores = storeMapper.selectByUserId(user_id);
        if (stores == null)
            jsonObject.put("message", "user does not own a store");
        else {
            JSONArray array = new JSONArray();
            for (Store store : stores){
                JSONObject content = new JSONObject();
                content.put("store_id", store.getStore_id());
                content.put("storename", store.getStorename());
                content.put("address", store.getAddress());
                content.put("book_num", store.getBookSet().size());
                content.put("introduction", store.getIntroduction());
                array.add(content);
            }
            jsonObject.put("contents", array);
            jsonObject.put("message", "success");
        }
        return jsonObject;
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
