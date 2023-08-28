package com.neu.his.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.CheckApply;
import com.neu.his.service.ICheckApplyService;
import com.neu.his.utils.Constants;
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
 * @ClassName: CheckApplyController.java
 * @Description: 检查申请 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/checkApplys")
public class CheckApplyController {
    @Resource
    private ICheckApplyService checkApplyService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<CheckApply> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "itemName");
        InitUtil.initEq(param, wrapper, "active");
        IPage<CheckApply> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(checkApplyService.page(page, wrapper));
    }

    @GetMapping("/list/{id}")
    public Result getCaselist(@RequestParam Map<String, Object> param, @PathVariable int id) {
        QueryWrapper<CheckApply> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1).eq("register_Id", id);
        InitUtil.initEq(param, wrapper, "status");
        return ResultGenerator.getSuccessResult(checkApplyService.list(wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<CheckApply> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<CheckApply> list = checkApplyService.list(wrapper);
        for (CheckApply i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getItemName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getCheckApply(@PathVariable int id) {
        CheckApply checkApply = checkApplyService.getById(id);
        if (checkApply == null) {
            return ResultGenerator.getFailResult("", "无检查申请记录");
        }
        return ResultGenerator.getSuccessResult(checkApply);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody CheckApply CheckApply) {
        // System.out.println(CheckApply);
        if (checkApplyService.save(CheckApply)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PostMapping("/{id}")
    @UserLoginToken
    public Result batchInsert(@RequestBody CheckApply CheckApply) {
        // System.out.println(CheckApply);
        if (checkApplyService.save(CheckApply)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody CheckApply checkApply, @PathVariable int id) {
        checkApply.setId(id);
        // System.out.println(CheckApply);
        if (checkApplyService.updateById(checkApply)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        CheckApply checkApply = new CheckApply();
        checkApply.setActive(active);
        checkApply.setId(id);
        if (checkApplyService.updateById(checkApply)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    /**
     * 收费
     */
    @PutMapping("/fee")
    @UserLoginToken
    public Result recevieFee(@RequestBody Map<String, Object> param) {
        String[] idList = param.get("ids").toString().split(",");
        CheckApply checkApply = new CheckApply();
        checkApply.setStatus(Constants.CHECK_APPLY_STATUS_2);
        for (String id : idList) {
            checkApply.setId(Integer.parseInt(id));
            if (!checkApplyService.updateById(checkApply)) {
                return ResultGenerator.getFailResult("", "收费失败，该检查项不存在");
            }
        }
        return ResultGenerator.getSuccessResult("", "收费成功");
    }

    /**
     * 退费
     */
    @PutMapping("/refund")
    @UserLoginToken
    public Result refund(@RequestBody Map<String, Object> param) {
        String[] idList = param.get("ids").toString().split(",");
        CheckApply checkApply = new CheckApply();
        checkApply.setStatus(Constants.CHECK_APPLY_STATUS_4);
        for (String id : idList) {
            checkApply.setId(Integer.parseInt(id));
            if (!checkApplyService.updateById(checkApply)) {
                return ResultGenerator.getFailResult("", "退费失败，该检查项不存在");
            }
        }
        return ResultGenerator.getSuccessResult("", "退费成功");
    }

    /**
     * 检查多项
     */
    @PutMapping("/checks")
    @UserLoginToken
    public Result checks(@RequestBody Map<String, Object> param) {
        String[] idList = param.get("ids").toString().split(",");
        CheckApply checkApply = new CheckApply();
        checkApply.setStatus(Constants.CHECK_APPLY_STATUS_3);
        for (String id : idList) {
            checkApply.setId(Integer.parseInt(id));
            if (!checkApplyService.updateById(checkApply)) {
                return ResultGenerator.getFailResult("", "检查失败，该检查项不存在");
            }
        }
        return ResultGenerator.getSuccessResult("", "检查成功");
    }

    /**
     * 检查一项
     */
    @PutMapping("/check/{id}")
    @UserLoginToken
    public Result checks(@PathVariable int id) {
        CheckApply checkApply = new CheckApply();
        checkApply.setStatus(Constants.CHECK_APPLY_STATUS_3);
        checkApply.setId(id);
        if (!checkApplyService.updateById(checkApply)) {
            return ResultGenerator.getFailResult("", "检查失败，该检查项不存在");
        }
        return ResultGenerator.getSuccessResult("", "检查成功");
    }


    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (checkApplyService.removeById(id)) {
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
        if (checkApplyService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
