package com.neu.his.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.his.entity.RolePermission;
import com.neu.his.service.IRolePermissionService;
import com.neu.his.utils.Result;
import com.neu.his.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: RolePermissionController.java
 * @Description: 角色权限 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:55:49
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/role-permission")
public class RolePermissionController {

    @Resource
    private IRolePermissionService roles;

    @GetMapping("/{id}")
    public Result getRightsById(@PathVariable int id) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        RolePermission role = roles.getOne(wrapper);
        if (role == null) {
            return ResultGenerator.getFailResult("该角色尚未分配权限");
        }
        return ResultGenerator.getSuccessResult(role);
    }

    @PostMapping()
    public Result add(@RequestBody RolePermission entity) {
        if (roles.save(entity)) {
            return ResultGenerator.getSuccessResult("", "分配权限成功！");
        }
        return ResultGenerator.getFailResult("", "分配权限失败");
    }

    @PutMapping("/{id}")
    public Result add(@RequestBody RolePermission entity, @PathVariable int id) {
        entity.setId(id);
        if (roles.updateById(entity)) {
            return ResultGenerator.getSuccessResult("", "修改分配权限成功！");
        }
        return ResultGenerator.getFailResult("", "修改分配权限失败");
    }

}
