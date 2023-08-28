package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.CheckItem;
import com.neu.his.service.ICheckItemService;
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
 * @ClassName: CheckItemController.java
 * @Description: 检查项目 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:52:53
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/checkItems")
public class CheckItemController {
    @Resource
    private ICheckItemService checkItemService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<CheckItem> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "name");
        InitUtil.initEq(param, wrapper, "active");
        IPage<CheckItem> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(checkItemService.page(page, wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<CheckItem> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<CheckItem> list = checkItemService.list(wrapper);
        for (CheckItem i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getCheckItem(@PathVariable int id) {
        CheckItem checkItem = checkItemService.getById(id);
        if (checkItem == null) {
            return ResultGenerator.getFailResult("", "无挂号级别记录");
        }
        return ResultGenerator.getSuccessResult(checkItem);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody CheckItem checkItem) {
        // System.out.println(checkItem);
        if (checkItemService.save(checkItem)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody CheckItem checkItem, @PathVariable int id) {
        checkItem.setId(id);
        // System.out.println(checkItem);
        if (checkItemService.updateById(checkItem)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        CheckItem checkItem = new CheckItem();
        checkItem.setActive(active);
        checkItem.setId(id);
        if (checkItemService.updateById(checkItem)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (checkItemService.removeById(id)) {
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
        if (checkItemService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
