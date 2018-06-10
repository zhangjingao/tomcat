package com.zjg.tomdog.annotations;

import java.lang.annotation.*;

/**
 * @author zjg
 * @date 2018/4/8 20:16
 * @Description
 */
@Documented //生成javadoc文档
@Target(ElementType.TYPE)  //注解表示的范围，接口、类、枚举、注解
@Retention(RetentionPolicy.RUNTIME) //注解生命周期，一直保留
public @interface WebService {

//    String name () default "";
}
