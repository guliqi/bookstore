package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.utils.CommonUtil;
import com.guliqi.bookstore.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
