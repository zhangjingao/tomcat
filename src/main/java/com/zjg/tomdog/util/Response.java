package com.zjg.tomdog.util;

import com.zjg.tomdog.handlecore.ExceptionHandle;
import com.zjg.tomdog.handlecoreimpl.ExceptionHandleImpl;

import java.io.*;
import java.net.Socket;

/**
 * @author zjg
 * @date 2018/4/11 0:05
 * @Description 响应类
 */
public class Response {

    /**
     * 做出响应给客户端
     * @param socket socket
     * @param path request source path
     */
    public void write (Socket socket , String path) {
        ExceptionHandle exceptionHandle = new ExceptionHandleImpl();
        File file;
        try {
            if (null == path) {
                path = exceptionHandle.cantHandlePath("请求信息为空");
            }
            path = System.getProperty("user.dir") + "\\"+"webapp"+"\\"+path;
            file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fis.read(bytes, 0, 1024)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            write(socket,exceptionHandle.noFoundPath("path"));
        } catch (IOException e) {
            write(socket,exceptionHandle.cantHandlePath("无法得到outputstream"));
        }
    }
}
