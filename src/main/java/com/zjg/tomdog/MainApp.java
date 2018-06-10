package com.zjg.tomdog;

import com.zjg.tomdog.handle.Handle;
import com.zjg.tomdog.annotations.ServletMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * 存放servlet名和地址的映射关系
     */
    public static Map<Class<?> , String> servlets = new HashMap<Class<?>, String>();


    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        System.out.println("tomdog is start");
        new ServletMapping().getAnnotation();
        Handle handle = new Handle();
        handle.handle();
    }

}

