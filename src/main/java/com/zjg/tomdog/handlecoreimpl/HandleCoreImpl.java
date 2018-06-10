package com.zjg.tomdog.handlecoreimpl;

import com.sun.deploy.util.ArrayUtil;
import com.zjg.tomdog.MainApp;
import com.zjg.tomdog.annotations.WebService;
import com.zjg.tomdog.handlecore.ExceptionHandle;
import com.zjg.tomdog.handlecore.HandleCore;
import com.zjg.tomdog.util.JudgeSource;
import com.zjg.tomdog.util.Request;
import com.zjg.tomdog.util.Response;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * @author zjg
 * @date 2018/3/26 21:41
 * @Description
 */
public class HandleCoreImpl implements HandleCore{

    private ExceptionHandle exceptionHandle = new ExceptionHandleImpl(); //异常处理

    @Override
    public Request read (Socket socket) {
        String url = null;
        try {
            InputStream inputStream = new BufferedInputStream(socket.getInputStream());
            byte reads = (byte) inputStream.read();
            byte[] bc = new byte[inputStream.available()+1];
            bc[0] = reads;
            inputStream.read(bc, 1, inputStream.available());
            url = new String(bc);

        } catch (SocketTimeoutException ex) {
            System.out.println("Read timed out");
        } catch (IOException e) {
            new ExceptionHandleImpl().cantHandlePath("连接超时");
            return null;
        }
        Request httpMessage = null;
        if (url != null) {
            httpMessage = new Request(url,socket);
        }
        return httpMessage;
    }

    @Override
    public void forword(Socket socket, Request requestMessage) {
        boolean isStatic = new JudgeSource().judgeSourceType(requestMessage.getData());
        String path = null;
        if (!isStatic) {
            Map<Class<?> , String> allServlet = MainApp.servlets;
            String requestData = requestMessage.getData();
            String requestMethod = requestMessage.getMethod();
            boolean isExists = false;//是否存在这个servlet
            try {
                Class<?> clazz = null;
                Method method = null;
                Response response = new Response();//响应对象
                for (Map.Entry<Class<?> , String> servlets: allServlet.entrySet()) {
                    String servletName = servlets.getKey().getSimpleName();
                    if (requestData.equals(servletName)) {
                        isExists = true;
                        clazz = Class.forName(servlets.getKey().getName());
                        if ("GET".equals(requestMethod)) {
                            method = clazz.getMethod("doGet",Request.class,Response.class);
                        } else if ("POST".equals(requestMethod)) {
                            method = clazz.getMethod("doPost",Request.class,Response.class);
                        }
                        assert method != null;
                        method.invoke(clazz.newInstance(),requestMessage,response);
                    }
                }
                if (!isExists) {
                    path = exceptionHandle.noFoundPath(requestData);//请求的类名
                }
            } catch (NoSuchMethodException e) {
                path = exceptionHandle.cantHandlePath("NoSuchMethodException: 不存在这样的方法");
            } catch (IllegalAccessException |InstantiationException | InvocationTargetException e) {
                path = exceptionHandle.cantHandlePath("反射机制使用异常");
            } catch (ClassNotFoundException e) {
                path = exceptionHandle.noFoundPath("ClassNotFoundException: 反射机制加载类失败，该类不存在");
            }
        } else {
//            System.out.println("静态资源");
            path = requestMessage.getData();
        }
        if (path != null) {
            new Response().write(socket,path);
        }
    }


    @Override
    public void closeSocket(Socket socket) {
        try {
            if (!socket.isInputShutdown()) {
                System.out.println("关闭输入流");
                socket.shutdownInput();
            }
            if (!socket.isOutputShutdown()) {
                System.out.println("关闭输出流");
                socket.shutdownOutput();
            }
            if (!socket.isClosed()) {
                System.out.println("关闭socket");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeTomdog () {
        System.exit(0);
    }

}
