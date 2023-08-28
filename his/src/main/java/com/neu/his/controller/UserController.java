package com.neu.his.controller;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.his.annotation.UserLoginToken;
import com.neu.his.entity.User;
import com.neu.his.entity.Work;
import com.neu.his.pool.ThreadPool;
import com.neu.his.service.IUserService;
import com.neu.his.service.IWorkService;
import com.neu.his.utils.InitUtil;
import com.neu.his.utils.Result;
import com.neu.his.utils.ResultGenerator;
import com.neu.his.utils.ShaUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: UserController.java
 * @Description: 用户 前端控制器
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月6日 下午2:56:01
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IWorkService workService;


    List<Work> workList = new ArrayList<>();
    @GetMapping
    public Result getList(@RequestParam Map<String, Object> param) {
        InitUtil.initPage(param);
        int num = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        Object name = param.get("name");
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("username", name).or().like("real_name", name);
        }
        InitUtil.initEq(param, wrapper, "active");
        // 参数一是当前页，参数二是每页个数
        IPage<User> page = new Page<>(num, limit);
        return ResultGenerator.getSuccessResult(userService.page(page, wrapper));
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable int id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResultGenerator.getFailResult("", "无用户记录");
        }
        return ResultGenerator.getSuccessResult(user);
    }

    @GetMapping("/check")
    public Result checkUserName(@RequestParam String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", name);
        if (userService.getOne(wrapper) != null) {
            return ResultGenerator.getFailResult("", "该用户名已存在");
        }
        return ResultGenerator.getSuccessResult();
    }

    @GetMapping("/list")
    public Result getListBy(@RequestParam Map<String, Object> param) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        InitUtil.initEq(param, wrapper, "user_type", "dept_id", "active");
        //工作时间为相似查找
        InitUtil.initLike(param, wrapper, "work_time");
        JSONObject jsonObject = new JSONObject();
        List<User> list = userService.list(wrapper);
        for (User i : list) {
            jsonObject.put(i.getId().toString(), Map.of("name", i.getRealName()));
        }
        return ResultGenerator.getSuccessResult(jsonObject);
    }

    @PostMapping()
    @UserLoginToken
    public Result save(@RequestBody User user) {
        user.setPassword(ShaUtil.getSHA256(user.getPassword()));
        if (userService.save(user)) {
            return ResultGenerator.getSuccessResult("", "添加成功");
        }
        return ResultGenerator.getFailResult("", "添加失败");
    }

    @PutMapping("/{id}")
    @UserLoginToken
    public Result update(@RequestBody User user, @PathVariable int id) {
        user.setId(id);
        User user1 = userService.getById(user);
        //密码非空且已更新则对密码进行加密
        if (user.getPassword() != null) {
            if (!Objects.equals(user.getPassword(), user1.getPassword())) {
                user.setPassword(ShaUtil.getSHA256(user.getPassword()));
            }
        } else {
            user.setPassword(user1.getPassword());
        }
        //完善user的空项
        User.compAndSet(user, user1);
        if (userService.updateById(user)) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }
        return ResultGenerator.getFailResult("", "更新失败");
    }

    /**
     * 修改用户的状态，相当于修改用户的激活状态
     */
    @PutMapping("/{id}/state/{active}")
    @UserLoginToken
    public Result changeActive(@PathVariable int id, @PathVariable int active) {
        User user = new User();
        user.setActive(active);
        user.setId(id);
        if (userService.updateById(user)) {
            return ResultGenerator.getSuccessResult("", "激活状态修改成功");
        }
        return ResultGenerator.getFailResult("", "激活状态修改失败");
    }

    @DeleteMapping("/{id}")
    @UserLoginToken
    public Result del(@PathVariable int id) {
        if (userService.removeById(id)) {
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
        if (userService.removeByIds(list)) {
            return ResultGenerator.getSuccessResult("", "删除成功");
        }
        return ResultGenerator.getFailResult("", "删除失败");
    }


    /**
     * 前端调用：更新医生排班表
     */
    @PutMapping("/update_work")
    @UserLoginToken
    public Result updateWork() {
        int ret = -3;
        try{
            ret=upDateWorkList();
        } catch (InterruptedException e) {
            ret=-2;
            e.printStackTrace();
        }
        if (ret == 0) {
            return ResultGenerator.getSuccessResult("", "更新成功");
        }else if(ret==-2){
            return ResultGenerator.getFailResult("", "线程池错误");
        }
        else {
            return ResultGenerator.getFailResult("", "更新失败");
        }
    }

    /**
     * 后端调用：更新医生排班表
     *
     * @return 执行状态0为成功，-1为失败
     */
    public int upDateWorkList() throws InterruptedException {
        workList.clear();
        //参与排班的医生身份
        int[] accessList = {3, 4, 5, 6, 7};
        //获取当前日期1-7代表周一到周日
        Date date0 = new Date();
        int dayNow = date0.getDay();
        if (dayNow == 0) {
            dayNow = 7;
        }
        int temp = 0;
        System.out.println(dayNow);
        //设置信号量，节点数
        final CountDownLatch countDownLatch=new CountDownLatch(7);
        //开始按天更新
        for (int j = 0; j < 7; j++) {
            //temp表示正在运算的是周几
            temp = dayNow + j;
            if (temp == 7) {
                dayNow -= 7;
            }
            //上午
            String temp1 = temp + "a";
            //下午
            String temp2 = temp + "b";
            int finalJ = j;
            ThreadPool.threadPool.execute(() -> {
                System.out.println("线程："+finalJ+"正在运行");
                insertFinishResult(getWorkInfo(temp1, accessList));
                insertFinishResult(getWorkInfo(temp2, accessList));
                //信号量减一
                countDownLatch.countDown();
            });

        }
        //超时时间设置60秒
        countDownLatch.await(60, TimeUnit.SECONDS);
        workList = sortList(workList);
        int ret = upDateSchedule(workList);
        System.out.println("————————————————————end————————————————————");
        if (ret == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 将多线程得到的workList按照标准格式格式化
     *
     * @param workList 多线程得到的混乱的list
     * @return 格式化后的list
     */
    private List<Work> sortList(List<Work> workList) {
        List<Work> resultWorkList = new ArrayList<>();
        Date date0 = new Date();
        int dayNow = date0.getDay();
        int temp = 0, startId = 1;
        for (int j = 0; j < 7; j++) {
            //temp表示正在运算的是周几
            temp = dayNow + j;
            if (temp == 7) {
                dayNow -= 7;
            }
            //上午
            String temp1 = temp + "a";
            //下午
            String temp2 = temp + "b";
            for (Work work : workList
            ) {
                if (Objects.equals(work.getWorkTime0(), temp1)) {
                    work.setId(startId);
                    startId++;
                    resultWorkList.add(work);
                }
            }
            for (Work work : workList
            ) {
                if (Objects.equals(work.getWorkTime0(), temp2)) {
                    work.setId(startId);
                    startId++;
                    resultWorkList.add(work);
                }
            }
        }
        return resultWorkList;
    }

    /**
     * 根据工作时间和身份找到对应的医生
     *
     * @param dateAndTime 工作时间
     * @param depts       权限等级
     * @return 对象集
     */
    private List<Work> getWorkInfo(String dateAndTime, int[] depts) {
        List<Work> workList = new ArrayList<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //查找工作时间有上午的
        wrapper.like("work_time", dateAndTime);
        //按部门号降序排序
        wrapper.orderByDesc("dept_id");
        List<User> list1 = userService.list(wrapper);
        for (User i : list1) {
            for (int tempDetpId : depts
            ) {
                if (i.getUserType() == tempDetpId) {
                    Work work = new Work();
                    work.setName(i.getRealName());
                    work.setUserType(i.getUserType());
                    work.setDeptId(i.getDeptId());
                    work.setNumbMax(10);
                    work.setWorkTime0(dateAndTime);
                    work.setNumb(0);
                    work.setState(1);
                    workList.add(work);
                }
            }
        }
        return workList;
    }

    /**
     * 将数据插入表格
     *
     * @param workList 要插入的数据
     * @return 结果：0正常-1失败
     */

    public int upDateSchedule(List<Work> workList) {
        try {
            System.out.println("准备插入数据");
            int count = workService.count();
            System.out.println("conut=" + count);
            List<Work> uList = workService.list();
            List<Integer> list = new ArrayList<>(count);
            for (Work wo : uList
            ) {
                list.add(wo.getId());
            }
            workService.removeByIds(list);
            System.out.println("表格已清空");
            //大量数据分批入库
            List<List<Work>> dataGroup = ListUtil.split(workList, 200);
            for (List<Work> workGroup : dataGroup) {
                workService.saveBatch(workGroup, 200);
                //workService.saveOrUpdateBatch(workGroup,200);
                System.out.println(workGroup);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将多线程查询中间值存到结果集中
     *
     * @param result   得到的中间值
     */
    private synchronized void insertFinishResult(List<Work> result) {
        workList.addAll(result);
    }

}
