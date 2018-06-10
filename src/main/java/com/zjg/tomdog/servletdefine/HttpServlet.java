package com.zjg.tomdog.servletdefine;

import com.zjg.tomdog.util.Request;
import com.zjg.tomdog.util.Response;

/**
 * @author zjg
 * @date 2018/4/9 21:30
 * @Description 定义servlet标准
 */
public interface HttpServlet {

    /**
     * 处理get请求
     * @param request 请求信息
     * @param response 响应信息
     */
    public void doGet (Request request , Response response);


    /**
     * 处理post请求
     * @param request 请求信息
     * @param response 响应信息
     */
    public void doPost (Request request , Response response);
}
