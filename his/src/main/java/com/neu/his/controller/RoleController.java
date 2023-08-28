package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.Role;
import com.neu.his.service.IRoleService;
import com.neu.his.utils.InitUtil;
import com.neu.his.utils.Result;
import com.neu.his.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: RoleController.java
 * @Description: 角色 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:55:35
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "name");
        InitUtil.initEq(param, wrapper, "active");
        IPage<Role> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(roleService.page(page, wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<Role> list = roleService.list(wrapper);
        for (Role i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getRole(@PathVariable int id) {
        Role role = roleService.getById(id);
        if (role == null) {
            return ResultGenerator.getFailResult("", "无该角色记录");
        }
        return ResultGenerator.getSuccessResult(role);
    }

    @GetMapping("/check")
    public Result checkUserName(@RequestParam String name) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        if (roleService.getOne(wrapper) != null) {
            return ResultGenerator.getFailResult("", "该角色名已存在");
        }
        return ResultGenerator.getSuccessResult();
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody Role role) {
        // System.out.println(role);
        if (roleService.save(role)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody Role role, @PathVariable int id) {
        role.setId(id);
        // System.out.println(role);
        if (roleService.updateById(role)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        Role role = new Role();
        role.setActive(active);
        role.setId(id);
        if (roleService.updateById(role)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (roleService.removeById(id)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }

    @DeleteMapping("/batchdel")
    @UserLoginToken
    public Result batchDel(@RequestParam String ids) {
        String[] idList = ids.split(",");
        List<Integer> list = new ArrayList<>(idList.length);
        for (String id : idList) {
            list.add(Integer.parseInt(id));
        }
        if (roleService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
