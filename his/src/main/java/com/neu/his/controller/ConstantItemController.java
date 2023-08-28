package com.neu.his.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.ConstantItem;
import com.neu.his.service.IConstantItemService;
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
 * @ClassName: ConstantItemController.java
 * @Description: 状态码表 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:53:04
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/constantItems")
public class ConstantItemController {

    @Resource
    private IConstantItemService constantItemService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<ConstantItem> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "name");
        InitUtil.initEq(param, wrapper, "active");
        wrapper.orderByAsc("type_id", "sort");
        IPage<ConstantItem> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(constantItemService.page(page, wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<ConstantItem> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        return ResultGenerator.getSuccessResult(constantItemService.list(wrapper));
    }


    @GetMapping("/{id}")
    public Result getConstantItem(@PathVariable int id) {
        ConstantItem constantItem = constantItemService.getById(id);
        if (constantItem == null) {
            return ResultGenerator.getFailResult("", "无常数选项记录");
        }
        return ResultGenerator.getSuccessResult(constantItem);
    }

    // @GetMapping("/check")
    // public Result checkCode(@RequestParam String name) {
    //     QueryWrapper<ConstantItem> wrapper = new QueryWrapper<>();
    //     wrapper.eq("code", name);
    //     if (constantItemService.getOne(wrapper) != null)
    //         return ResultGenerator.getFailResult("", "常数项名称已存在");
    //     return ResultGenerator.getSuccessResult();
    // }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody ConstantItem constantItem) {
        // System.out.println(constantItem);
        if (constantItemService.save(constantItem)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody ConstantItem constantItem, @PathVariable int id) {
        constantItem.setId(id);
        // System.out.println(constantItem);
        if (constantItemService.updateById(constantItem)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        ConstantItem constantItem = new ConstantItem();
        constantItem.setActive(active);
        constantItem.setId(id);
        if (constantItemService.updateById(constantItem)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (constantItemService.removeById(id)) {
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
        if (constantItemService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }

}
