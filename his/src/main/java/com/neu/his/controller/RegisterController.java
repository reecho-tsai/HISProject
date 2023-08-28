package com.neu.his.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.Register;
import com.neu.his.service.IRegisterService;
import com.neu.his.utils.Constants;
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
 * @ClassName: RegisterController.java
 * @Description: 诊疗信息 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:55:07
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/registers")
public class RegisterController {
    @Resource
    private IRegisterService registerService;

    // private Register find(int id){
    //   Register register = registerService.getById(id);
    //   if (register == null){
    //     throw new NotExistException();
    //   }
    //   return register;
    // }

    @GetMapping
    @UserLoginToken
    public Result getlist(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<Register> wrapper = new QueryWrapper<>();
        Object name = param.get("name");
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name).or().like("id", name);
        }
        InitUtil.initEq(param, wrapper, "active", "status", "doctor_id", "dept_id");
        IPage<Register> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(registerService.page(page, wrapper));
    }

    @GetMapping("/all")
    public Result getAll() {
        QueryWrapper<Register> wrapper = new QueryWrapper<>();
        wrapper.eq("active", 1);
        JSONObject jsonObject = new JSONObject();
        List<Register> list = registerService.list(wrapper);
        for (Register i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @GetMapping("/{id}")
    public Result getRegister(@PathVariable int id) {
        Register register = registerService.getById(id);
        if (register == null) {
            return ResultGenerator.getFailResult("", "无挂号级别记录");
        }
        return ResultGenerator.getSuccessResult(register);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody Register register) {
        // System.out.println(register);
        register.setStatus(Constants.REGISTER_REGIST); //默认添加的数据为已挂号状态
        if (registerService.save(register)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody Register register, @PathVariable int id) {
        register.setId(id);
        // System.out.println(register);
        if (registerService.updateById(register)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        Register register = new Register();
        register.setActive(active);
        register.setId(id);
        if (registerService.updateById(register)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    /**
     * 接诊
     */
    @PutMapping("/receive/{id}")
    @UserLoginToken
    public Result receive(@PathVariable int id) {
        Register register = new Register();
        register.setId(id);
        register.setStatus(Constants.REGISTER_DEAL);
        if (registerService.updateById(register)) {
            return ResultGenerator.getSuccessResult("", "接诊成功");
        }
        return ResultGenerator.getFailResult("", "接诊失败");
    }

    /**
     * 退号
     */
    @PutMapping("/num/{id}")
    @UserLoginToken
    public Result returnNum(@PathVariable int id) {
        Register register = new Register();
        register.setId(id);
        register.setStatus(Constants.REGISTER_RETURN);
        if (registerService.updateById(register)) {
            return ResultGenerator.getSuccessResult("", "退号成功");
        }
        return ResultGenerator.getFailResult("", "退号失败");
    }


    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (registerService.removeById(id)) {
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
        if (registerService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }
}
