package com.zjg.tomdog.handlecore;

/**
 * @author zjg
 * @date 2018/4/8 10:09
 * @Description 封装异常处理
 */
public interface ExceptionHandle {

    /**
     * handlecoreimpl 404 exception
     * @return 404 page path
     */
    public String noFoundPath (String pathMsg);

    /**
     * handlecoreimpl 500 exception
     * @return 500 page path
     */
    public String cantHandlePath(String errorMsg);
}
