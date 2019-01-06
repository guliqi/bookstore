package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.model.Order;
import com.guliqi.bookstore.service.OrderService;
import com.guliqi.bookstore.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/request/order")
public class OrderController {
    private OrderService orderService;
    private TokenService tokenService;

    @Autowired
    public OrderController(OrderService orderService, TokenService tokenService){
        this.orderService = orderService;
        this.tokenService = tokenService;
    }

    @ApiOperation("测试提交订单")
    @PostMapping(value = "")
    @UserLoginToken
    public JSONObject submitOrder(@RequestHeader String token, @RequestBody Order order){
        String user_id = tokenService.getIdOrName(token);
        return orderService.submitOrder(order, user_id);
    }

    @ApiOperation("测试订单支付")
    @PutMapping(value = "/pay")
    @UserLoginToken
    public JSONObject pay(@RequestHeader String token, @RequestParam String order_id){
        String user_id = tokenService.getIdOrName(token);
        return orderService.pay(order_id, user_id);
    }

    @ApiOperation("测试查看未发货订单")
    @GetMapping(value = "/toBeShipped")
    @UserLoginToken
    public JSONObject toBeShipped(@RequestHeader String token, @RequestParam String store_id){
        String user_id = tokenService.getIdOrName(token);
        return orderService.toBeShipped(store_id, user_id);
    }

    @ApiOperation("测试确认发货")
    @PutMapping(value = "/deliver")
    @UserLoginToken
    public JSONObject deliver(@RequestHeader String token, @RequestParam String order_id){
        String user_id = tokenService.getIdOrName(token);
        return orderService.deliver(order_id, user_id);
    }

    @ApiOperation("测试确认收货")
    @PutMapping(value = "/receive")
    @UserLoginToken
    public JSONObject receive(@RequestHeader String token, @RequestParam String order_id){
        String user_id = tokenService.getIdOrName(token);
        return orderService.receive(order_id, user_id);
    }
}
