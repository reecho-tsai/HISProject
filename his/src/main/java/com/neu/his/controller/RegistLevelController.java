package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.RegistLevel;
import com.neu.his.service.IRegistLevelService;
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
 * @ClassName: RegistLevelController.java
 * @Description: 挂号级别 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:55:22
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/registLevels")
public class RegistLevelController {
    @Resource
    private IRegistLevelService registLevelService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<RegistLevel> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "name");
        InitUtil.initEq(param, wrapper, "active");
        IPage<RegistLevel> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(registLevelService.page(page, wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<RegistLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<RegistLevel> list = registLevelService.list(wrapper);
        for (RegistLevel i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName(), "fee", i.getFee(), "roleId", i.getRoleId()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getRegistLevel(@PathVariable int id) {
        RegistLevel registLevel = registLevelService.getById(id);
        if (registLevel == null) {
            return ResultGenerator.getFailResult("", "无挂号级别记录");
        }
        return ResultGenerator.getSuccessResult(registLevel);
    }

    @GetMapping("/check")
    public Result checkCode(@RequestParam String name) {
        QueryWrapper<RegistLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        RegistLevel registLevel = registLevelService.getOne(wrapper);
        if (registLevel != null) {
            return ResultGenerator.getFailResult("", "该常数代码已存在");
        }
        return ResultGenerator.getSuccessResult();
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody RegistLevel registLevel) {
        // System.out.println(registLevel);
        if (registLevelService.save(registLevel)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody RegistLevel registLevel, @PathVariable int id) {
        registLevel.setId(id);
        // System.out.println(registLevel);
        if (registLevelService.updateById(registLevel)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        RegistLevel registLevel = new RegistLevel();
        registLevel.setActive(active);
        registLevel.setId(id);
        if (registLevelService.updateById(registLevel)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (registLevelService.removeById(id)) {
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
        if (registLevelService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
