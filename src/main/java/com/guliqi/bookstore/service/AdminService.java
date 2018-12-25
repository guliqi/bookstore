package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.AdminMapper;
import com.guliqi.bookstore.model.Admin;
import com.guliqi.bookstore.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper){
        this.adminMapper = adminMapper;
    }

    public JSONObject login(Admin admin){
        JSONObject jsonObject = new JSONObject();
        admin.setPassword(MD5Util.MD5Encode(admin.getPassword()));
        Admin retAdmin = adminMapper.findOne(admin);
        if (retAdmin == null)
            jsonObject.put("message", "wrong username or password");
        else{
            jsonObject.put("message", "success");
            jsonObject.put("admin", admin.getUsername());
        }
        return jsonObject;
    }
}
