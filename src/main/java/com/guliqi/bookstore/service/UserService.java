package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.Constants;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.Order;
import com.guliqi.bookstore.model.Store;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.utils.CommonUtil;
import com.guliqi.bookstore.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public JSONObject register(User user){
        JSONObject jsonObject = new JSONObject();
        String password = user.getPassword();
        user.setPassword(null);
        User retUser = userMapper.selectOne(user);
        if (retUser != null)
            jsonObject.put("message", "the Phone number has been registered");
        else {
            user.setPassword(MD5Util.MD5Encode(password));
            user.setUser_id(CommonUtil.UUID());
            if (userMapper.insert(user) < 1)
                jsonObject.put("message", "insert failed");
            else {
                jsonObject.put("message", "success");
                jsonObject.put("contents", user);
            }
        }
        return jsonObject;
    }

    public JSONObject login(User user){
        JSONObject jsonObject = new JSONObject();
        user.setPassword(MD5Util.MD5Encode(user.getPassword()));
        User retUser = userMapper.selectOne(user);
        if (retUser == null)
            jsonObject.put("message", "wrong username or password");
        else{
            user.setUser_id(retUser.getUser_id());
            jsonObject.put("message", "success");
            jsonObject.put("contents", retUser);
        }
        return jsonObject;
    }

    public JSONObject getProfile(String user_id){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.detailSelectById(user_id);
        if (user == null)
            jsonObject.put("message", "user does not exists");
        else {
            jsonObject.put("message", "success");
            jsonObject.put("contents", user);
        }
        return jsonObject;
    }

    public JSONObject getInformation(String user_id, int kind){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.selectById(user_id);
        jsonObject.put("message", "success");
        switch (kind){
            case Constants.STORE: {
                jsonObject.put("contents", user.getStoreSet());
                break;
            }
            case Constants.ADDRESS: {
                jsonObject.put("contents", user.getAddressSet());
                break;
            }
            case Constants.ORDER: {
                jsonObject.put("contents", user.getOrderSet());
                break;
            }
            case Constants.APPLICATION: {
                jsonObject.put("contents", user.getApplicationSet());
                break;
            }
            case Constants.SHOPPINGLIST: {
                jsonObject.put("contents", user.getShoppinglistSet());
                break;
            }
            default:break;
        }
        return jsonObject;
    }

    public JSONObject isStoreOwner(String user_id){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.selectById(user_id);
        boolean isOwner = false;
        for (Store store : user.getStoreSet()){
            if (store.getShopKeeper().getUser_id().equals(user_id)){
                isOwner = true;
                break;
            }
        }
        if (isOwner)
            jsonObject.put("message", "success");
        else jsonObject.put("message", "not the owner");
        return jsonObject;
    }
}
