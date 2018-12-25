package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.Constants;
import com.guliqi.bookstore.mapper.ApplicationMapper;
import com.guliqi.bookstore.model.Application;
import com.guliqi.bookstore.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    private ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationService(ApplicationMapper applicationMapper){
        this.applicationMapper = applicationMapper;
    }

    public JSONObject applyForStore(Application application){
        JSONObject jsonObject = new JSONObject();
        if (application.getAddress() == null || application.getBank_card() == null ||
                application.getIntroduction() == null || application.getStorename() == null ||
                application.getUser() == null)
            jsonObject.put("message", "incomplete information");
        else {
            application.setState(Constants.CHECKPENDING);
            application.setApplication_id(CommonUtil.UUID());
            if (applicationMapper.insert(application) < 1)
                jsonObject.put("message", "Insert failed");
            else {
                jsonObject.put("message", "success");
                jsonObject.put("contents", application);
            }
        }
        return jsonObject;
    }

    public JSONObject checkApplication(Application application){
        JSONObject jsonObject = new JSONObject();

        if (!application.getState().equals(Constants.APPROVED) && !application.getState().equals(Constants.DISAPPROVED)) {
            jsonObject.put("message", "invalid State");
            return jsonObject;
        }
        Application current = applicationMapper.selectById(application.getApplication_id());
        if (!current.getState().equals(Constants.CHECKPENDING))
            jsonObject.put("message", "already checked");
        else {
            if (applicationMapper.updateStateById(application) < 1)
                jsonObject.put("message", "update failed");
            else {
                jsonObject.put("message", "success");
                jsonObject.put("contents", application);
            }
        }
        return jsonObject;
    }
}
