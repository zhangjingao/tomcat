package com.zjg.tomdog.handle;

import com.zjg.tomdog.thread.ThreadPoolFactory;
import com.zjg.tomdog.thread.ThreadTask;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zjg
 * @date 2018/3/26 16:38
 * @Description
 */
public class Handle {

    /**
     * 监听并处理请求
     */
    public void handle() {
        ThreadPoolFactory poolFactory = ThreadPoolFactory.getInstance();
        try {
            ServerSocket serverSocket = new ServerSocket(80);//监听80端口
            while (true) { //等待请求
                Socket socket = serverSocket.accept();//当服务器接受一个请求时就创建一个socket
                poolFactory.addTask(new ThreadTask(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
