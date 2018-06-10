package com.zjg.tomdog.controller;

import com.zjg.tomdog.servletdefine.HttpServlet;
import com.zjg.tomdog.util.Request;
import com.zjg.tomdog.util.Response;

/**
 * @author zjg
 * @date 2018/3/26 16:43
 * @Description 实验类，没加注解，不能接收请求
 */
public class BackController implements HttpServlet {


    public void doGet(Request request , Response response) {
        System.out.println("do get ... do something ... tologin");
        response.write(request.getSocket(),"index.html");
    }


    public void doPost(Request request , Response response) {
        System.out.println("do post ... do something");
        response.write(request.getSocket(),"homepage.html");
    }

    /**
     * 处理若干逻辑，登陆成功
     * @return path
     */
    public String resister () {
        return System.getProperty("user.dir") + "\\"+"webapp"+"\\"+"resister.jsp";
    }

}
