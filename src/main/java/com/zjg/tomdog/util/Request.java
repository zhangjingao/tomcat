package com.zjg.tomdog.util;

import com.zjg.tomdog.handlecore.ExceptionHandle;

import javax.sound.midi.Soundbank;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zjg
 * @date 2018/3/26 17:02
 * @Description 接收到的http信息
 */
public class Request {

    private String protocol;//协议类型

    private String data;//请求数据

    private String method;//请求方法（post/get）

    private Map<String , String> attrs; //参数

    private Socket socket;


    /**
     * 请求方法
     * @param protocol 协议
     * @param data 请求数据
     * @param method 请求方法
     */
    public Request(String protocol, String data, String method) {
        this.protocol = protocol;
        this.data = data;
        this.method = method;
    }

    /**
     * 请求方法
     * @param request 完整的url
     */
    public Request(String request,Socket socket) {
        this.getRequestMess(request);
        this.socket = socket;
    }

    /**
     * 得到request信息
     * @param request request url
     */
    private void getRequestMess (String request) {
        String [] methodUrlPro = request.split("\n")[0].split(" ");
        if (methodUrlPro.length == 3) {
            this.method = methodUrlPro[0];
            this.protocol = methodUrlPro[2];
            String [] urlAttr = methodUrlPro[1].split("\\?");//分隔参数和url
            this.data = urlAttr[0].substring(1,urlAttr[0].length());
            String [] attrArray , keyValue;//所有参数，每个参数的参数名和值
            if (urlAttr.length > 1) {
                attrArray = urlAttr[1].split("&");
                for (String s : attrArray) {
                    keyValue = s.split("=");
                    this.addAttrs(keyValue[0],keyValue[1]);
                }
            }
        }
        System.out.println("request message:"+request);
    }

    /**
     * 添加参数
     * @param key 参数名
     * @param value 值
     */
    private void addAttrs(String key,String value) {
        if (attrs == null) {
            attrs = new HashMap<String , String>();
        }
        this.attrs.put(key,value);
    }
    public void setProtocol (String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        return "\n{ \nprotocol: "+protocol+"\ndata: "+data+"\nmethod: "+method+"\nattrs："+attrs+"\n}";
    }
}


