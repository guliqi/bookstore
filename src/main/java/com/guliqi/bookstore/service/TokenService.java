package com.guliqi.bookstore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.guliqi.bookstore.model.Admin;
import com.guliqi.bookstore.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    public String getToken(User user) {
        String token;
        token = JWT.create().withAudience(user.getUser_id())
                .withClaim("auth", "user")
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 3600))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    public String getToken(Admin admin){
        String token;
        token = JWT.create().withAudience(admin.getUsername())
                .withClaim("auth", "admin")
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1000 * 3600))
                .sign(Algorithm.HMAC256(admin.getPassword()));
        return token;
    }

    public String getIdOrName(String token) {
        return JWT.decode(token).getAudience().get(0);
    }
}

