package com.baogetv.app.model.usercenter.entity;

/**
 * Created by chalilayang on 2017/11/20.
 */

public class UserData {
    private int userId;
    private String iconUrl;
    private String nickName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
