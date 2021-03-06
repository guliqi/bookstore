package com.guliqi.bookstore.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.guliqi.bookstore.mapper.AdminMapper;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.Admin;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                if (token == null)
                    throw new RuntimeException("无效的token，请重新登录");
                if (redisService.hasKey(token))
                    throw new RuntimeException("无效的token，请重新登录");

                if (method.isAnnotationPresent(OnlyAdmin.class)) {
                    OnlyAdmin onlyAdmin = method.getAnnotation(OnlyAdmin.class);
                    if (onlyAdmin.required()){
                        String ath = JWT.decode(token).getClaim("auth").asString();
                        if (!ath.equals("admin"))
                            throw new RuntimeException("仅管理员可以操作");
                    }
                    String adminName;
                    try {
                        adminName = JWT.decode(token).getAudience().get(0);
                    } catch (JWTDecodeException j){
                        throw new RuntimeException("token解码错误");
                    }
                    Admin admin = adminMapper.selectByPrimaryKey(adminName);
                    if (admin == null)
                        throw new RuntimeException("无效的token，请重新登录");
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
                    try {
                        jwtVerifier.verify(token);
                    } catch (JWTVerificationException e) {
                        throw new RuntimeException("验证失败");
                    }
                    return true;
                }
                else {
                    String userId;
                    try {
                        userId = JWT.decode(token).getAudience().get(0);
                    } catch (JWTDecodeException j) {
                        throw new RuntimeException("token解码错误");
                    }
                    User user = userMapper.selectPwdById(userId);
                    if (user == null)
                        throw new RuntimeException("无效的token，请重新登录");
                    // 验证 token
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                    try {
                        jwtVerifier.verify(token);
                    } catch (JWTVerificationException e) {
                        throw new RuntimeException("无效的token，请重新登录");
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}