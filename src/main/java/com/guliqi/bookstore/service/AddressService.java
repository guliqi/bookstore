package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.AddressMapper;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.Address;
import com.guliqi.bookstore.model.Store;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressMapper addressMapper;
    private UserMapper userMapper;


    @Autowired
    public AddressService(AddressMapper addressMapper, UserMapper userMapper){
        this.addressMapper = addressMapper;
        this.userMapper = userMapper;
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

    public JSONObject removeAddress(String address_id, String user_id){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.selectById(user_id);
        for (Store store: user.getStoreSet()){
            if (store.getAddress().getAddress_id().equals(address_id)){
                jsonObject.put("message", "address in use");
                return jsonObject;
            }
        }
        for (Address addr : user.getAddressSet()){
            if (addr.getAddress_id().equals(address_id)){
                if (addressMapper.deleteById(address_id) < 1)
                    jsonObject.put("message", "delete failed");
                else
                    jsonObject.put("message", "success");
                return jsonObject;
            }
        }
        jsonObject.put("message", "address does not exists");
        return jsonObject;
    }

    public JSONObject updateAddress(Address address, String user_id){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.selectById(user_id);
        for (Address addr : user.getAddressSet()){
            if (addr.equals(address)){
                if (addressMapper.updateById(address) < 1)
                    jsonObject.put("message", "update failed");
                else
                    jsonObject.put("message", "success");
                return jsonObject;
            }
        }
        jsonObject.put("message", "address does not exists");
        return jsonObject;
    }
}
