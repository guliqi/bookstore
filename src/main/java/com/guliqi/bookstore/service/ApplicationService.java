package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.Constants;
import com.guliqi.bookstore.mapper.ApplicationMapper;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.Address;
import com.guliqi.bookstore.model.Application;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApplicationService {
    private ApplicationMapper applicationMapper;
    private UserMapper userMapper;

    @Autowired
    public ApplicationService(ApplicationMapper applicationMapper, UserMapper userMapper){
        this.applicationMapper = applicationMapper;
        this.userMapper = userMapper;
    }

    public JSONObject applyForStore(String user_id, String storename, String address_id,
                                    String bank_card, String introduction){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.selectById(user_id);
        Address inputAddress = new Address(address_id);
        boolean addressExists = false;
        for (Address address : user.getAddressSet()){
            if (address.equals(inputAddress)){
                addressExists =true;
                break;
            }
        }
        if (!addressExists)
            jsonObject.put("message", "address does not exist");
        else {
            Application application = new Application.Builder().user(user).address(inputAddress)
                    .state(Constants.APPLY_CHECKPENDING).application_id(CommonUtil.UUID())
                    .storename(storename).bank_card(bank_card).introduction(introduction).build();
            if (applicationMapper.insert(application) < 1)
                jsonObject.put("message", "insert failed");
            else {
                jsonObject.put("message", "success");
                jsonObject.put("application_id", application.getApplication_id());
            }
        }
        return jsonObject;
    }

    public JSONObject checkApplication(String admin, String application_id, String state){
        JSONObject jsonObject = new JSONObject();
        if (!state.equals(Constants.APPLY_APPROVED) && !state.equals(Constants.APPLY_DISAPPROVED)) {
            jsonObject.put("message", "invalid State");
            return jsonObject;
        }
        String currentState = applicationMapper.selectState(application_id);
        if (!currentState.equals(Constants.APPLY_CHECKPENDING))
            jsonObject.put("message", "already checked");
        else {
            Application application = new Application.Builder().application_id(application_id)
                    .state(state).admin(admin).build();
            if (applicationMapper.updateStateById(application) < 1)
                jsonObject.put("message", "update failed");
            else {
                jsonObject.put("message", "success");
            }
        }
        return jsonObject;
    }

    public JSONObject lookOver(){
        JSONObject jsonObject = new JSONObject();
        Set<Application> applications = applicationMapper.selectAllCheckPending();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(applications);
        jsonObject.put("message", "success");
        jsonObject.put("contents", jsonArray);
        return jsonObject;
    }
}
