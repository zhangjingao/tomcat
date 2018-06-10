package com.zjg.tomdog.handlecore;

import com.zjg.tomdog.util.Request;

import java.net.Socket;

/**
 * @author zjg
 * @date 2018/4/7 19:14
 * @Description 处理请求核心接口
 */
public interface HandleCore {

    /**
     * 读取请求信息
     * @param socket 套接字
     * @return request message
     */
    public Request read (Socket socket);

    /**
     * 处理分析 request，得到请求的资源路径
     *
     * @param socket 套接字
     * @param requestMessage request message
     */
    public void forword(Socket socket, Request requestMessage);

    /**
     * 关闭输入输出流
     * @param socket 套接字
     */
    public void closeSocket(Socket socket);

    /**
     * close tomdog
     */
    public void closeTomdog();

}
