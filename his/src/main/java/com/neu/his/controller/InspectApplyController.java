package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.InspectApply;
import com.neu.his.service.IInspectApplyService;
import com.neu.his.utils.Constants;
import com.neu.his.utils.InitUtil;
import com.neu.his.utils.Result;
import com.neu.his.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: InspectApplyController.java
 * @Description: 检验申请 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:54:13
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/inspectApplys")
public class InspectApplyController {

    private Logger log = LoggerFactory.getLogger(InspectApplyController.class);

    @Resource
    private IInspectApplyService inspectApplyService;

    @GetMapping
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<InspectApply> wrapper = new QueryWrapper<>();
        InitUtil.initLike(param, wrapper, "itemName");
        InitUtil.initEq(param, wrapper, "active");
        IPage<InspectApply> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(inspectApplyService.page(page, wrapper));
    }

    @GetMapping("/list/{id}")
    public Result getCaselist(@RequestParam Map<String, Object> param, @PathVariable int id) {
        log.debug("list/" + id + "; param=" + param);
        QueryWrapper<InspectApply> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1).eq("register_id", id);
        InitUtil.initEq(param, wrapper, "status");
        return ResultGenerator.getSuccessResult(inspectApplyService.list(wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<InspectApply> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<InspectApply> list = inspectApplyService.list(wrapper);
        for (InspectApply i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getItemName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getInspectApply(@PathVariable int id) {
        InspectApply inspectApply = inspectApplyService.getById(id);
        if (inspectApply == null) {
            return ResultGenerator.getFailResult("", "无检查申请记录");
        }
        return ResultGenerator.getSuccessResult(inspectApply);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody InspectApply inspectApply) {
        // System.out.println(InspectApply);
        if (inspectApplyService.save(inspectApply)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody InspectApply inspectApply, @PathVariable int id) {
        inspectApply.setId(id);
        // System.out.println(InspectApply);
        if (inspectApplyService.updateById(inspectApply)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        InspectApply inspectApply = new InspectApply();
        inspectApply.setActive(active);
        inspectApply.setId(id);
        if (inspectApplyService.updateById(inspectApply)) {
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
        InspectApply inspectApply = new InspectApply();
        inspectApply.setStatus(Constants.CHECK_APPLY_STATUS_2);
        for (String id : idList) {
            inspectApply.setId(Integer.parseInt(id));
            if (!inspectApplyService.updateById(inspectApply)) {
                return ResultGenerator.getFailResult("", "收费失败，该检验项不存在");
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
        InspectApply inspectApply = new InspectApply();
        inspectApply.setStatus(Constants.CHECK_APPLY_STATUS_4);
        for (String id : idList) {
            inspectApply.setId(Integer.parseInt(id));
            if (!inspectApplyService.updateById(inspectApply)) {
                return ResultGenerator.getFailResult("", "退费失败，该检验项不存在");
            }
        }
        return ResultGenerator.getSuccessResult("", "退费成功");
    }

    /**
     * 检验多项
     */
    @PutMapping("/check")
    @UserLoginToken
    public Result check(@RequestBody Map<String, Object> param) {
        String[] idList = param.get("ids").toString().split(",");
        InspectApply inspectApply = new InspectApply();
        inspectApply.setStatus(Constants.CHECK_APPLY_STATUS_3);
        for (String id : idList) {
            inspectApply.setId(Integer.parseInt(id));
            if (!inspectApplyService.updateById(inspectApply)) {
                return ResultGenerator.getFailResult("", "检验失败，该检验项不存在");
            }
        }
        return ResultGenerator.getSuccessResult("", "检验成功");
    }

    /**
     * 检验一项
     */
    @PutMapping("/check/{id}")
    @UserLoginToken
    public Result checks(@PathVariable int id) {
        InspectApply inspectApply = new InspectApply();
        inspectApply.setStatus(Constants.CHECK_APPLY_STATUS_3);
        inspectApply.setId(id);
        if (!inspectApplyService.updateById(inspectApply)) {
            return ResultGenerator.getFailResult("", "检查失败，该检验项不存在");
        }
        return ResultGenerator.getSuccessResult("", "检查成功");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (inspectApplyService.removeById(id)) {
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
        if (inspectApplyService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
