package com.zjg.tomdog.controller;

import com.zjg.tomdog.annotations.WebService;
import com.zjg.tomdog.servletdefine.HttpServlet;
import com.zjg.tomdog.util.Request;
import com.zjg.tomdog.util.Response;

/**
 * @author zjg
 * @date 2018/4/9 21:26
 * @Description
 */
@WebService
public class BackServlet implements HttpServlet{

    @Override
    public void doGet(Request request , Response response) {
        System.out.println("do get ... do something ...... tologin");
        response.write(request.getSocket(),"index.html");
    }

    @Override
    public void doPost(Request request , Response response) {
        System.out.println("do post ...... do something");
        response.write(request.getSocket(),"homepage.html");
    }

}
