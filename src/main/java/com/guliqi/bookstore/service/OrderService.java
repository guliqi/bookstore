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

    //简单模拟支付
    public JSONObject mockPay(Order order, String user_id){
        JSONObject jsonObject = new JSONObject();

        Order o = orderMapper.selectById(order.getOrder_id());

        if (o == null || !o.getUser().getUser_id().equals(user_id))
            jsonObject.put("message", "Order does not exists");
        else if (!o.getState().equals(Constants.ORDER_UNPAID))
            jsonObject.put("message", "already paid");
        else {
            order.setState(Constants.ORDER_PAID);
            if (orderMapper.updateById(order) < 1)
                jsonObject.put("message", "update failed");
            else jsonObject.put("message", "success");
        }
        return jsonObject;
    }
}
