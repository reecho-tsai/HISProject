package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.his.entity.User;
import com.neu.his.service.IUserService;
import com.neu.his.utils.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: LoginController.java
 * @Description: 登陆控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:54:38
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
public class LoginController {

    @Resource
    private IUserService userService;

    /**
     * 通过账号和密码登录
     *
     * @param param 请求参数列表
     * @return 结果集
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> param) {
        System.out.println(param);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("telephone", param.get("telephone")).eq("password", ShaUtil.getSHA256(param.get("password").toString()));
        User user = userService.getOne(wrapper);
        if (user == null) {
            return ResultGenerator.getFailResult(null, "手机号或密码错误");
        }
        if (user.getActive() == 0) {
            return ResultGenerator.getFailResult(null, "该用户尚未激活，请联系管理员帮你激活");
        }
        return getResult(user);
    }

    /**
     * 向目标手机号发送验证码
     *
     * @param param 请求参数列表
     * @return 结果集
     */
    @PostMapping("/sendCode")
    public Result sendCode(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(param);
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int tempCode = random.nextInt(10);
            code.append(tempCode);
        }
        System.out.println(code);
        System.out.println(param.get("telephone"));
        session.setAttribute("code", String.valueOf(code));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("telephone", param.get("telephone"));
        jsonObject.put("code", String.valueOf(code));
        int ret = MessageUtil.sendMessage((String) param.get("telephone"), String.valueOf(code));
        if (ret == 0) {
            return ResultGenerator.getSuccessResult(jsonObject, "短信发送成功！");
        } else {
            return ResultGenerator.getFailResult(null, "发送失败！");
        }
    }

    /**
     * 通过短信验证码登录
     *
     * @param param 请求参数列表
     * @return 结果集
     */
    @PostMapping("/loginByCode")
    public Result loginByCode(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(param);
        String realCode = (String) session.getAttribute("code");
        System.out.println("realcode: " + realCode);
        /*
        if (!Objects.equals((String) param.get("code"), realCode)) {
            return ResultGenerator.getFailResult(null, "验证码错误");
        }
        */
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("telephone", param.get("telephone"));
        User user = userService.getOne(wrapper);
        if (user == null) {
            return ResultGenerator.getFailResult(null, "用户不存在");
        }
        if (user.getActive() == 0) {
            return ResultGenerator.getFailResult(null, "该用户尚未激活，请联系管理员帮你激活");
        }
        return getResult(user);
    }

    /**
     * 成功登录后构建结果集
     *
     * @param user user对象
     * @return 结果集
     */
    private Result getResult(User user) {
        user.setLastLogin(LocalDateTime.now());
        updateLoginTime(user.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", JwtUtil.create(user));
        jsonObject.put("id", user.getId());
        jsonObject.put("deptId", user.getDeptId());
        return ResultGenerator.getSuccessResult(jsonObject, "登录成功");
    }


    private void updateLoginTime(int id) {
        User user = new User();
        user.setId(id);
        user.setLastLogin(LocalDateTime.now());
        userService.updateById(user);
    }

}