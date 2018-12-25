package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.AddressMapper;
import com.guliqi.bookstore.model.Address;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressMapper addressMapper;

    @Autowired
    public AddressService(AddressMapper addressMapper){
        this.addressMapper = addressMapper;
    }

    public JSONObject addAddress(Address address, String user_id){
        JSONObject jsonObject = new JSONObject();
        address.setAddress_id(CommonUtil.UUID());
        address.setUser(new User(user_id));
        if (addressMapper.insert(address) < 1)
            jsonObject.put("message", "insert failed");
        else {
            jsonObject.put("message", "success");
        }
        return jsonObject;
    }

//    public JSONObject removeAddress(){
//
//    }
//
//    public JSONObject modifyAddress(){
//
//    }
}
