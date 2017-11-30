package com.baogetv.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/11/26.
 */

public class LoginBean implements Parcelable {


    /**
     * user_id : 1
     * openid :
     * username : aa
     * mobile :
     * sex : 0
     * birthday : 0000-00-00
     * intro :
     * height : 0
     * weight : 0
     * bfr : 0.00
     * pic_url : 0
     * score : 30
     * level_id : 1
     * level_time : 1508856360
     * is_sure : 1
     * dumb_time : 0
     * login : 3
     * reg_ip :
     * reg_time : 1508762250
     * last_login_ip : 0.0.0.0
     * last_login_time : 1510065717
     * status : 1
     * token : a5386df95ada78abd668e2d6ec12983f
     */

    private String user_id;
    private String openid;
    private String username;
    private String mobile;
    private String sex;
    private String birthday;
    private String intro;
    private String height;
    private String weight;
    private String bfr;
    private String pic_url;
    private String score;
    private String level_id;
    private String level_time;
    private String is_sure;
    private String dumb_time;
    private String login;
    private String reg_ip;
    private String reg_time;
    private String last_login_ip;
    private String last_login_time;
    private String status;
    private String token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBfr() {
        return bfr;
    }

    public void setBfr(String bfr) {
        this.bfr = bfr;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getLevel_time() {
        return level_time;
    }

    public void setLevel_time(String level_time) {
        this.level_time = level_time;
    }

    public String getIs_sure() {
        return is_sure;
    }

    public void setIs_sure(String is_sure) {
        this.is_sure = is_sure;
    }

    public String getDumb_time() {
        return dumb_time;
    }

    public void setDumb_time(String dumb_time) {
        this.dumb_time = dumb_time;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.openid);
        dest.writeString(this.username);
        dest.writeString(this.mobile);
        dest.writeString(this.sex);
        dest.writeString(this.birthday);
        dest.writeString(this.intro);
        dest.writeString(this.height);
        dest.writeString(this.weight);
        dest.writeString(this.bfr);
        dest.writeString(this.pic_url);
        dest.writeString(this.score);
        dest.writeString(this.level_id);
        dest.writeString(this.level_time);
        dest.writeString(this.is_sure);
        dest.writeString(this.dumb_time);
        dest.writeString(this.login);
        dest.writeString(this.reg_ip);
        dest.writeString(this.reg_time);
        dest.writeString(this.last_login_ip);
        dest.writeString(this.last_login_time);
        dest.writeString(this.status);
        dest.writeString(this.token);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.user_id = in.readString();
        this.openid = in.readString();
        this.username = in.readString();
        this.mobile = in.readString();
        this.sex = in.readString();
        this.birthday = in.readString();
        this.intro = in.readString();
        this.height = in.readString();
        this.weight = in.readString();
        this.bfr = in.readString();
        this.pic_url = in.readString();
        this.score = in.readString();
        this.level_id = in.readString();
        this.level_time = in.readString();
        this.is_sure = in.readString();
        this.dumb_time = in.readString();
        this.login = in.readString();
        this.reg_ip = in.readString();
        this.reg_time = in.readString();
        this.last_login_ip = in.readString();
        this.last_login_time = in.readString();
        this.status = in.readString();
        this.token = in.readString();
    }

    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
