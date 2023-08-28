package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.ConstantType;
import com.neu.his.service.IConstantTypeService;
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
 * @ClassName: ConstantTypeController.java
 * @Description: 状态码类别 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:53:49
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/constantTypes")
public class ConstantTypeController {

    @Resource
    private IConstantTypeService constantTypeService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<ConstantType> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "name");
        InitUtil.initEq(param, wrapper, "active");
        IPage<ConstantType> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(constantTypeService.page(page, wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<ConstantType> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<ConstantType> list = constantTypeService.list(wrapper);
        for (ConstantType i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getConstantType(@PathVariable int id) {
        ConstantType constantType = constantTypeService.getById(id);
        if (constantType == null) {
            return ResultGenerator.getFailResult("", "无常数类别记录");
        }
        return ResultGenerator.getSuccessResult(constantType);
    }

    @GetMapping("/check")
    public Result checkCode(@RequestParam String name) {
        QueryWrapper<ConstantType> wrapper = new QueryWrapper<>();
        wrapper.eq("code", name);
        ConstantType constantType = constantTypeService.getOne(wrapper);
        if (constantType != null) {
            return ResultGenerator.getFailResult("", "该常数代码已存在");
        }
        return ResultGenerator.getSuccessResult();
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody ConstantType constantType) {
        // System.out.println(constantType);
        if (constantTypeService.save(constantType)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody ConstantType constantType, @PathVariable int id) {
        constantType.setId(id);
        // System.out.println(constantType);
        if (constantTypeService.updateById(constantType)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        ConstantType constantType = new ConstantType();
        constantType.setActive(active);
        constantType.setId(id);
        if (constantTypeService.updateById(constantType)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (constantTypeService.removeById(id)) {
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
        if (constantTypeService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
