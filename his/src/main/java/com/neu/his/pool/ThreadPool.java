package com.neu.his.pool;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: ThreadPool.java
 * @Description: 线程池
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月9日 下午3:36:58
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月6日     msprc           v1.0.0               修改原因
 */
public class ThreadPool {
    /**
     * 类实例
     */
    public static ThreadPool instance = null;

    /**
     * 定长线程池
     */
    public static ExecutorService threadPool = null;

    /**
     * 线程池初始化配置函数
     */
    public ThreadPool() {
        // 获取系统处理器个数，作为线程池数量
        int nThreads = Runtime.getRuntime().availableProcessors();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().build();
        // 本池中操作均为IO密集型，设定线程池最大线程数 = 可获取的逻辑处理器个数 * 2 + 1
        int corePoolSize = nThreads * 2 + 1;
        //考虑到医院服务器可能较老，设置默认最大值为8
        // 线程池最大大小
        int maximumPoolSize = Math.max(corePoolSize, 8);
        // 缓冲队列大小
        int bufferCapacity = Math.max(corePoolSize, 8);
        // 当提交的任务数大于corePoolSize时，会优先放到队列缓冲区，只有填满了缓冲区后，才会判断当前运行的任务是否大于maxPoolSize，
        // 小于时会新建线程处理。大于时就触发了拒绝策略，
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(bufferCapacity),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    }

    /**
     * 使用懒加载形式进行类初始化
     *
     * @return 线程池实例
     */
    public static synchronized ThreadPool getInstance() {
        if (instance == null) {
            // 使用懒加载的形式进行初始化
            instance = new ThreadPool();
        }
        return instance;
    }

    /**
     * 向线程池中添加任务
     *
     * @param taskNum 执行的任务号
     *                1为更新排班表
     */
    public void addTask(int taskNum) {
        // 初始化一个哈希表存放运行结果
        threadPool.execute(() -> {
            if (taskNum == 1) {
                System.out.println("1");
            }
        });
    }

}
