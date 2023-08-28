package com.neu.his.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.neu.his.entity.User;
import com.neu.his.exception.MyException;
import com.neu.his.exception.UserNoLoginException;
import com.neu.his.service.IUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: JwtUtil.java
 * @Description: token记住密码模块
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午3:03:09
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@Component
public class JwtUtil {

    /**
     * token私钥
     */
    public static final String KEY = "neu_user_his";
    /**
     * 过期时间为1小时
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    private static IUserService userService;

    /**
     * 根据用户创建唯一的token
     * @param user 用户对象
     * @return token
     */
    public static String create(User user) {

        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return JWT.create()
                .withHeader(header)
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withClaim("deptId", user.getDeptId())
                .withClaim("timestamp", Date.from(user.getLastLogin().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(KEY));
    }

    public static void isExpire(String token) {
        if (System.currentTimeMillis() - JWT.decode(token).getExpiresAt().getTime() > 0) {
            throw new MyException("登录已过期，请重新登录");
        }
    }

    /**
     * 通过token串 获取id信息，然后从数据库中获取信息
     *
     * @param token 前端保留的token
     * @return 用户对象
     */
    public static User getUserById(String token) {
        Integer userId = JWT.decode(token).getClaim("id").asInt();
        System.out.println(userService + ":" + userId);
        User user = userService.getById(userId);
        if (user == null) {
            throw new UserNoLoginException();
        }
        return user;
    }

    /**
     * 根据token获取用户对象
     * @param token 前端保存的token
     * @return 用户对象
     */

    public static User getUser(String token) {
        try {
            User user = new User();
            user.setId(JWT.decode(token).getClaim("id").asInt());
            user.setUsername(JWT.decode(token).getClaim("username").asString());
            user.setPassword(JWT.decode(token).getClaim("password").asString());
            user.setDeptId(JWT.decode(token).getClaim("deptId").asInt());
            Date date = JWT.decode(token).getClaim("timestamp").asDate();
            user.setLastLogin(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(e.getMessage());
        }
    }

    /**
     * 测试&展示段
     */
    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin");
        user.setLastLogin(LocalDateTime.now());
        System.out.println(user);
        String token = create(user);
        System.out.println("-------------------token----------------");
        System.out.println(getUser(token));
    }

    @Resource
    public void setUserService(IUserService userService) {
        JwtUtil.userService = userService;
    }
}