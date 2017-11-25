package com.baogetv.app.bean;

import java.util.List;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class SearchHotWordBean {

    /**
     * status : 1
     * msg :
     * url :
     * data : ["狗粮","小鲜肉","魔鬼","健身","拉伸","热力","狗粮","小鲜肉","魔鬼","健身","拉伸","热力狗粮","小鲜肉","魔鬼","健身",
     * "拉伸","热力"]
     */

    private int status;
    private String msg;
    private String url;
    private List<String> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
