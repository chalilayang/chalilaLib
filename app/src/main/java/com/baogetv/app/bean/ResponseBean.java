package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class ResponseBean<T> {
    private int status;
    private String msg;
    private String url;
    private T data;

    public T getData() {
        return data;
    }
}
