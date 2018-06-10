package com.zjg.tomdog.annotations;


import com.zjg.tomdog.MainApp;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public class ServletMapping {

    /**
     * 映射注解
     */
    public void getAnnotation () {
        Map<Class<?> , String> allClass = getClasses("com.zjg.tomdog");
        for (Map.Entry<Class<?>, String> clas : allClass.entrySet()) {
            boolean useAnnota = clas.getKey().isAnnotationPresent(WebService.class);
            if (useAnnota) {
                MainApp.servlets.put(clas.getKey(),clas.getValue());
            }
        }
    }

    /**
     * 从包package中获取所有的Class
     * @return
     */
    public static Map<Class<?> , String> getClasses(String packageName){

//        第一个class类的集合
//        List<Class<?>> classes = new ArrayList<Class<?>>();
        Map<Class<?> , String> allClass = new HashMap<Class<?> , String>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive,allClass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allClass;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param allClass
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
                                                        boolean recursive, Map<Class<?>, String> allClass){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive, allClass);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
//                    添加到集合中去
                    allClass.put(Class.forName(packageName + '.' + className),packageName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}