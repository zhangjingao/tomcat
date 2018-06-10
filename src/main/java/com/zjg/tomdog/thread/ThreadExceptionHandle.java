package com.zjg.tomdog.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zjg
 * @date 2018/4/7 15:45
 * @Description 当线程数达到最大，并且队列满员时，开启拒绝策略
 */
public class ThreadExceptionHandle implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("the task is so mush that overflow，we will give up this task," +
                "we will accept new task until the blockingqueue have free! task is :" +r);
    }
}
