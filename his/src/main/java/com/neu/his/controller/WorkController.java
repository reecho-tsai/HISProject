package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.Work;
import com.neu.his.service.IWorkService;
import com.neu.his.utils.InitUtil;
import com.neu.his.utils.Result;
import com.neu.his.utils.ResultGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: WorkController.java
 * @Description: 用户 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月8日 上午8:54:38
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月8日     gxy           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/works")
public class WorkController {

    @Resource
    private IWorkService workService;

    @GetMapping
    public Result getList(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<Work> wrapper = new QueryWrapper<Work>();
        Object name = param.get("name");
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        //InitUtil.initEq(param, wrapper, "active");
        // 参数一是当前页，参数二是每页个数
        IPage<Work> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(workService.page(page, wrapper));
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable int id) {
        Work work = workService.getById(id);
        if (work == null) {
            return ResultGenerator.getFailResult("", "无用户记录");
        }
        return ResultGenerator.getSuccessResult(work);
    }

    @GetMapping("/check")
    public Result checkUserName(@RequestParam String name) {
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        if (workService.getOne(wrapper) != null) {
            return ResultGenerator.getFailResult("", "该用户名已存在");
        }
        return ResultGenerator.getSuccessResult();
    }

    @GetMapping("/list")
    public Result getListBy(@RequestParam Map<String, Object> param) {
        QueryWrapper<Work> wrapper = new QueryWrapper<>();
        InitUtil.initEq(param, wrapper, "user_type", "dept_id", "state", "work_time0");
        JSONObject jsonObject = new JSONObject();
        List<Work> list = workService.list(wrapper);
        for (Work i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody Work work) {
        //System.out.println(work);
        if (workService.save(work)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody Work work, @PathVariable int id) {
        work.setId(id);
        //System.out.println(work);
        if (workService.updateById(work)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    /**
     * 修改用户的状态，相当于修改用户的激活状态
     */
    @PutMapping("/{id}/state/{state}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int state) {
        Work work = new Work();
        work.setState(state);
        work.setId(id);
        if (workService.updateById(work)) {
            return ResultGenerator.getSuccessResult("", "工作状态修改成功");
        }
        return ResultGenerator.getFailResult("", "工作状态修改失败");
    }

    /**
     * 修改挂号人数
     */
    @PutMapping("/add/{id}")
    @UserLoginToken
    public Result addNum(@PathVariable int id) {//挂号人数增减，修改numb和state
        Work work = workService.getById(id);
        int tempid = work.getId();
        int temp = work.getNumb() + 1;
        int max = work.getNumbMax();
        work.setNumb(temp);
        if (temp == max) {
            //等于挂号上限，状态变为0
            work.setState(0);
        }
        update(work, tempid);
        System.out.println(work.getName());
        return ResultGenerator.getSuccessResult();
    }

    /**
     * 修改挂号人数
     */
    @PutMapping("/minus/{id}")
    @UserLoginToken
    public Result minusNum(@PathVariable int id) {//挂号人数增减，修改numb和state
        Work work = workService.getById(id);
        int tempid = work.getId();
        int temp = work.getNumb() - 1;
        int max = work.getNumbMax();
        work.setNumb(temp);
        if (temp == max - 1) {
            //小于挂号上限，状态变为1
            work.setState(1);
        }
        update(work, tempid);
        System.out.println(work.getName());
        return ResultGenerator.getSuccessResult();
    }


    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (workService.removeById(id)) {
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
        if (workService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
