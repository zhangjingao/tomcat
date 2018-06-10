package com.zjg.tomdog.thread;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zjg
 * @date 2018/4/7 14:27
 * @Description 线程池工厂类
 */
public class ThreadPoolFactory {

    //线程池
    private static ThreadPoolExecutor threadPoolExecutor;
    //线程池配置信息
    private static ThreadPoolConfig poolConfig;
    //自身对象，单例
    private static ThreadPoolFactory threadFactory = null;


    /**
     * 构造方法私有化
     */
    private ThreadPoolFactory() {}

    /**
     * 获取工厂对象
     * @return 线程池工厂对象
     */
    public static ThreadPoolFactory getInstance () {
        if (poolConfig == null) {
            poolConfig = ThreadPoolConfig.getInstance();
        }
        if (threadFactory == null) {
            threadFactory = new ThreadPoolFactory();
        }
        if (threadPoolExecutor == null) {
            if (poolConfig.getBlockingQueue() == null) {
                threadPoolExecutor = new ThreadPoolExecutor(
                        poolConfig.getCorePoolSize(),poolConfig.getMaxPoolSize(),
                        poolConfig.getKeepAliveTime(),poolConfig.getAliveTimeUnit(),
                        poolConfig.getBlockingQueue());
            } else {
                threadPoolExecutor = new ThreadPoolExecutor(
                        poolConfig.getCorePoolSize(),poolConfig.getMaxPoolSize(),
                        poolConfig.getKeepAliveTime(),poolConfig.getAliveTimeUnit(),
                        poolConfig.getBlockingQueue(),poolConfig.getHandler()
                );
            }
        }
        System.out.println("create successfully pool");
        return threadFactory;
    }

    /**
     * 添加线程任务
     * @param runnable 一个线程任务
     */
    public synchronized void addTask (Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 添加线程任务
     * @param runnables 多个线程任务
     */
    public synchronized void addTask (List<Runnable> runnables) {
        if (runnables != null) {
            runnables.forEach(this::addTask);
        }
    }

    /**
     * 关闭线程池
     */
    public void closePool () {
        threadPoolExecutor.shutdown();
    }
}
