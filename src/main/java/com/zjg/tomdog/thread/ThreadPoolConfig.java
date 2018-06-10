package com.zjg.tomdog.thread;

import java.util.concurrent.*;

/**
 * @author zjg
 * @date 2018/4/7 13:58
 * @Description
 */
public class ThreadPoolConfig {

    //核心线程池大小
    private int corePoolSize = 10;
    //最大可创建的线程数
    private int maxPoolSize = 15;
    //线程空闲时存活时间
    private int keepAliveTime = 60;
    //存活时间单位
    private TimeUnit aliveTimeUnit = TimeUnit.SECONDS;
    //阻塞队列
    private BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(3);
    //线程池
    private ThreadPoolFactory threadFactory = null;
    //超出接受范围时的处理程序
    private RejectedExecutionHandler handler = new ThreadExceptionHandle();

    /* 类本身对象，单例模式 */
    private static ThreadPoolConfig threadPoolConfig;

    /**
     * 私有化构造方法
     */
    private ThreadPoolConfig () {}

    /**
     * 获取线程池配置文件对象
     * @return ThreadPoolConfig
     */
    public static ThreadPoolConfig getInstance() {
        if (threadPoolConfig == null) {
            return new ThreadPoolConfig();
        }
        return threadPoolConfig;
    }

    public RejectedExecutionHandler getHandler() {
        return handler;
    }

    public void setHandler(RejectedExecutionHandler handler) {
        this.handler = handler;
    }

    public ThreadPoolFactory getThreadFactory() {
        return threadFactory;
    }

    public void setThreadFactory(ThreadPoolFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public BlockingQueue<Runnable> getBlockingQueue() {
        return blockingQueue;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public TimeUnit getAliveTimeUnit() {
        return aliveTimeUnit;
    }
}
