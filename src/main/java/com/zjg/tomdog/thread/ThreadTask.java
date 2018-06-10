package com.zjg.tomdog.thread;

import com.zjg.tomdog.handlecore.ExceptionHandle;
import com.zjg.tomdog.handlecore.HandleCore;
import com.zjg.tomdog.handlecoreimpl.ExceptionHandleImpl;
import com.zjg.tomdog.handlecoreimpl.HandleCoreImpl;
import com.zjg.tomdog.util.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zjg
 * @date 2018/4/4 20:30
 * @Description
 */
public class ThreadTask extends Thread {

    private Socket socket = null;

    private final HandleCore handleCore = new HandleCoreImpl();//核心方法类

    private final ExceptionHandle exceptionHandle = new ExceptionHandleImpl();

    public ThreadTask (Socket socket) {
        this.socket = socket;
    }

   @Override
   public void run () {
       int timeOut = 3; //3秒超时
       Date start = new Date();//第一次进入时间
       Date end;
       Request requestMessage = null;
       while (true) {
           end = new Date();//再次请求时间
           long requestTime = (end.getTime() - start.getTime())/1000;
           try {
               if (requestTime > timeOut) {
                   handleCore.closeSocket(socket);
                   break;
               } else {
                   start = end;//不超时则重新赋值计算超时的时间
               }
               socket.setSoTimeout(3*1000);//设置超时时间为3秒，超出抛出异常
               requestMessage = handleCore.read(socket); //读取request请求
               if (null != requestMessage) {
                   handleCore.forword(socket, requestMessage);//加载请求的类，方法
               } else {
                   handleCore.closeSocket(socket);
                   break;
               }
           } catch (Exception e) {
               e.printStackTrace();
               exceptionHandle.cantHandlePath("接收请求或关闭socket异常");
               handleCore.closeSocket(socket);
               break;
           }
       }
   }

}
