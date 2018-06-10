package com.zjg.tomdog.handlecoreimpl;

import com.zjg.tomdog.handlecore.ExceptionHandle;

/**
 * @author zjg
 * @date 2018/4/5 17:51
 * @Description
 */
public class ExceptionHandleImpl implements ExceptionHandle {

    @Override
    public String noFoundPath (String pathMsg){
        System.out.println("fail to lookup "+pathMsg);
        return "404.html";
    }

    @Override
    public String cantHandlePath (String errorMsg){
        System.out.println(errorMsg);
        return "500.html";
    }

}
