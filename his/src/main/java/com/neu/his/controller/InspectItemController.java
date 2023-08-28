package com.neu.his.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.InspectItem;
import com.neu.his.service.IInspectItemService;
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
 * @ClassName: InspectItemController.java
 * @Description: 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:54:25
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/inspectItems")
public class InspectItemController {
    @Resource
    private IInspectItemService inspectItemService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<InspectItem> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "name");
        InitUtil.initEq(param, wrapper, "active");
        IPage<InspectItem> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(inspectItemService.page(page, wrapper));
    }


    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<InspectItem> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<InspectItem> list = inspectItemService.list(wrapper);
        for (InspectItem i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getInspectItem(@PathVariable int id) {
        InspectItem inspectItem = inspectItemService.getById(id);
        if (inspectItem == null) {
            return ResultGenerator.getFailResult("", "无挂号级别记录");
        }
        return ResultGenerator.getSuccessResult(inspectItem);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody InspectItem inspectItem) {
        // System.out.println(inspectItem);
        if (inspectItemService.save(inspectItem)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody InspectItem inspectItem, @PathVariable int id) {
        inspectItem.setId(id);
        // System.out.println(inspectItem);
        if (inspectItemService.updateById(inspectItem)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        InspectItem inspectItem = new InspectItem();
        inspectItem.setActive(active);
        inspectItem.setId(id);
        if (inspectItemService.updateById(inspectItem)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (inspectItemService.removeById(id)) {
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
        if (inspectItemService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
