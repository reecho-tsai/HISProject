package com.neu.his.utils;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ScheduleUtil.java
 * @Description: 计划任务
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月9日 上午9:54:38
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */

import com.neu.his.controller.UserController;
import com.neu.his.pool.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 1、@EnableScheduling 开启定时任务
 * 2、@Scheduled开启一个定时任务
 * 异步任务
 * 1、@EnableAsync:开启异步任务
 * 2、@Async：给希望异步执行的方法标注
 *
 * @author msprc
 */
@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class ScheduleUtil {

    ThreadPool threadPool = ThreadPool.getInstance();
    /*
     * 1、在Spring中表达式是6位组成，不允许第七位的年份
     * 2、在周几的的位置,1-7代表周一到周日
     * 3、定时任务不该阻塞。默认是阻塞的
     *      1）、可以让业务以异步的方式，自己提交到线程池
     *              CompletableFuture.runAsync(() -> {
     *         },execute);
     *      2）、支持定时任务线程池；设置 TaskSchedulingProperties
     *        spring.task.scheduling.pool.size: 5
     *      3）、让定时任务异步执行
     *          异步任务
     *      解决：使用异步任务 + 定时任务来完成定时任务不阻塞的功能
     *
     */

    /**
     * 每天夜间十二点执行计划任务：更新医生排班表
     * 时间配置规则看：<a href="http://www.zhano.cn/index.php/Java/49758.html">...</a>
     * 每天凌晨十二点刷新：@Scheduled(cron = "0 0 12 * * ?")
     * 每五秒刷新一次：@Scheduled(cron = "* /5 * * * * ?")  *和/中的空格要删除
     */
    @Async
    @Scheduled(cron = "0 0 12 * * ?")
    public void upDateSchedule() {
        UserController userController = new UserController();
        try{
            userController.upDateWorkList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
