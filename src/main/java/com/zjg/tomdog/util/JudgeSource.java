package com.zjg.tomdog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zjg
 * @date 2018/4/7 21:10
 * @Description
 */
public class JudgeSource {

    /**
     * 判断资源属于动态或静态,处理一些常见的资源后续可追加
     * @param path 资源文件路径
     */
    public boolean judgeSourceType(String path) {
        String [] staticSource = {".html",".css",".js","ico"};
        boolean isStatic = false;
        for (String source : staticSource ) {
            if (path.endsWith(source)) {
                isStatic = true;
            }
        }
        return isStatic;
    }
}
