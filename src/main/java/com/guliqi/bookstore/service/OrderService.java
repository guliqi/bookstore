package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.Constants;
import com.guliqi.bookstore.mapper.BookMapper;
import com.guliqi.bookstore.mapper.OrderMapper;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.*;
import com.guliqi.bookstore.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderMapper orderMapper;
    private BookMapper bookMapper;
    private UserMapper userMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, BookMapper bookMapper, UserMapper userMapper){
        this.orderMapper = orderMapper;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
    }

    public JSONObject submitOrder(Order order, String user_id){
        JSONObject jsonObject = new JSONObject();
        Book book = bookMapper.selectById(order.getBook().getBook_id());
        User user = userMapper.selectById(user_id);
        boolean addressExists = false;
        for (Address address : user.getAddressSet()){
            if (address.equals(order.getAddress())){
                addressExists = true;
                break;
            }
        }
        if (!addressExists)
            jsonObject.put("message", "address does not exists");
        else {
            if (book == null)
                jsonObject.put("message", "book does not exists");
            else {
                Store store = book.getStore();
                order.setStore(store);
                order.setPayment(book.getPrice() * order.getAmount());
                order.setOrder_id(CommonUtil.UUID());
                order.setUser(new User(user_id));
                order.setState(Constants.ORDER_UNPAID);
                if (orderMapper.insert(order) < 1)
                    jsonObject.put("message", "insert failed");
                else {
                    jsonObject.put("order_id", order.getOrder_id());
                    jsonObject.put("message", "success");
                }
            }
        }
        return jsonObject;
    }

    public JSONObject pay(String order_id, String user_id){
        JSONObject jsonObject = new JSONObject();
        Order order = orderMapper.selectById(order_id);
        if (order == null || !order.getUser().getUser_id().equals(user_id))
            jsonObject.put("message", "order does not exists");
        else if (!order.getState().equals(Constants.ORDER_UNPAID))
            jsonObject.put("message", "wrong state");
        else {
            order.setState(Constants.ORDER_PAID);
            if (orderMapper.updateStateById(order) < 1)
                jsonObject.put("message", "update failed");
            else jsonObject.put("message", "success");
        }
        return jsonObject;
    }

    public JSONObject deliver(String order_id, String user_id){
        JSONObject jsonObject = new JSONObject();
        Order order = orderMapper.selectById(order_id);
        if (order == null)
            jsonObject.put("message", "order does not exists");
        else if (!order.getState().equals(Constants.ORDER_PAID))
            jsonObject.put("message", "wrong state");
        else {
            Store store = order.getStore();
            User user = userMapper.selectById(user_id);
            boolean storeExists = false;
            for (Store s : user.getStoreSet()){
                if (s.equals(store)){
                    storeExists = true;
                    break;
                }
            }
            if (!storeExists)
                jsonObject.put("message", "store does not exists");
            else {
                order.setState(Constants.ORDER_DELIVERED);
                if (orderMapper.updateStateById(order) < 1)
                    jsonObject.put("message", "update failed");
                else jsonObject.put("message", "success");
            }
        }
        return jsonObject;
    }

    public JSONObject receive(String order_id, String user_id){
        JSONObject jsonObject = new JSONObject();
        Order order = orderMapper.selectById(order_id);
        if (order == null || !order.getUser().getUser_id().equals(user_id))
            jsonObject.put("message", "order does not exists");
        else if (!order.getState().equals(Constants.ORDER_DELIVERED))
            jsonObject.put("message", "wrong state");
        else {
            order.setState(Constants.ORDER_INACTIVE);
            if (orderMapper.updateStateById(order) < 1)
                jsonObject.put("message", "update failed");
            else jsonObject.put("message", "success");
        }
        return jsonObject;
    }

    public JSONObject toBeShipped(String store_id, String user_id){
        JSONObject jsonObject = new JSONObject();
        User user = userMapper.selectById(user_id);
        boolean storeExists = false;
        for (Store store : user.getStoreSet()){
            if (store.getStore_id().equals(store_id)){
                storeExists = true;
                break;
            }
        }
        if (!storeExists)
            jsonObject.put("message", "store does not exists");
        else {
            jsonObject.put("message", "success");
            jsonObject.put("contents", orderMapper.selectTobeShipped(store_id));
        }
        return jsonObject;
    }
}
