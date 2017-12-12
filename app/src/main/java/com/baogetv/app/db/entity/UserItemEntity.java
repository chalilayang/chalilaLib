package com.baogetv.app.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by chalilayang on 2017/10/19.
 */
@Entity
public class UserItemEntity {

    /**
     * user_id : 9
     * openid :
     * username : 山南
     * mobile : 13821049089
     * device_token : AgNPWWcJ965E_IAmOLh4V2dIJsLMmWDcQE_8a609vg2c
     * sex : 1
     * birthday : 1991-01-01
     * intro :
     * height : 0
     * weight : 0
     * bfr : 0
     * pic : 0
     * score : 36
     * level_id : 1
     * level_time : 2017-12-03 00:23:51
     * grade : 2
     * is_sure : 0
     * dumb_time : 1970-01-01 08:00:00
     * is_push_comments : 1
     * is_push_likes : 1
     * read_message_ids :
     * del_message_ids :
     * login : 17
     * reg_ip : 2099501355
     * reg_time : 2017-12-01 12:10:19
     * last_login_ip : 42.80.233.3
     * last_login_time : 2017-12-03 13:24:16
     * status : 1
     * token : ad748196435a35e5d9fa89a120af158d
     * pic_url :
     * level_name : LV1
     * medal : lv1
     * level_pic_url : http://120.77.176.101/jianshen/Uploads/Picture/2017-11-19/5a114121a67ac.jpg
     * next_level_score : 666
     * age : 26
     */
    @Id
    private Long id;
    @Property
    private String user_id;
    @Property
    private String openid;
    @Property
    private String username;
    @Property
    private String mobile;
    @Property
    private String device_token;
    @Property
    private String sex;
    @Property
    private String birthday;
    @Property
    private String intro;
    @Property
    private String height;
    @Property
    private String weight;
    @Property
    private String bfr;
    @Property
    private String pic;
    @Property
    private String score;
    @Property
    private String level_id;
    @Property
    private String level_time;
    @Property
    private String grade;
    @Property
    private String is_sure;
    @Property
    private String dumb_time;
    @Property
    private String is_push_comments;
    @Property
    private String is_push_likes;
    @Property
    private String read_message_ids;
    @Property
    private String del_message_ids;
    @Property
    private String login;
    @Property
    private String reg_ip;
    @Property
    private String reg_time;
    @Property
    private String last_login_ip;
    @Property
    private String last_login_time;
    @Property
    private String status;
    @Property
    private String token;
    @Property
    private String pic_url;
    @Property
    private String level_name;
    @Property
    private String medal;
    @Property
    private String level_pic_url;
    @Property
    private String next_level_score;
    @Property
    private int age;
    @Generated(hash = 1116634630)
    public UserItemEntity(Long id, String user_id, String openid, String username, String mobile, String device_token,
                           String sex, String birthday, String intro, String height, String weight, String bfr, String pic, String score,
                           String level_id, String level_time, String grade, String is_sure, String dumb_time, String is_push_comments,
                           String is_push_likes, String read_message_ids, String del_message_ids, String login, String reg_ip,
                           String reg_time, String last_login_ip, String last_login_time, String status, String token, String pic_url,
                           String level_name, String medal, String level_pic_url, String next_level_score, int age) {
        this.id = id;
        this.user_id = user_id;
        this.openid = openid;
        this.username = username;
        this.mobile = mobile;
        this.device_token = device_token;
        this.sex = sex;
        this.birthday = birthday;
        this.intro = intro;
        this.height = height;
        this.weight = weight;
        this.bfr = bfr;
        this.pic = pic;
        this.score = score;
        this.level_id = level_id;
        this.level_time = level_time;
        this.grade = grade;
        this.is_sure = is_sure;
        this.dumb_time = dumb_time;
        this.is_push_comments = is_push_comments;
        this.is_push_likes = is_push_likes;
        this.read_message_ids = read_message_ids;
        this.del_message_ids = del_message_ids;
        this.login = login;
        this.reg_ip = reg_ip;
        this.reg_time = reg_time;
        this.last_login_ip = last_login_ip;
        this.last_login_time = last_login_time;
        this.status = status;
        this.token = token;
        this.pic_url = pic_url;
        this.level_name = level_name;
        this.medal = medal;
        this.level_pic_url = level_pic_url;
        this.next_level_score = next_level_score;
        this.age = age;
    }
    public UserItemEntity(String user_id, String openid, String username, String mobile, String device_token,
                          String sex, String birthday, String intro, String height, String weight, String bfr, String pic, String score,
                          String level_id, String level_time, String grade, String is_sure, String dumb_time, String is_push_comments,
                          String is_push_likes, String read_message_ids, String del_message_ids, String login, String reg_ip,
                          String reg_time, String last_login_ip, String last_login_time, String status, String token, String pic_url,
                          String level_name, String medal, String level_pic_url, String next_level_score, int age) {
        this.user_id = user_id;
        this.openid = openid;
        this.username = username;
        this.mobile = mobile;
        this.device_token = device_token;
        this.sex = sex;
        this.birthday = birthday;
        this.intro = intro;
        this.height = height;
        this.weight = weight;
        this.bfr = bfr;
        this.pic = pic;
        this.score = score;
        this.level_id = level_id;
        this.level_time = level_time;
        this.grade = grade;
        this.is_sure = is_sure;
        this.dumb_time = dumb_time;
        this.is_push_comments = is_push_comments;
        this.is_push_likes = is_push_likes;
        this.read_message_ids = read_message_ids;
        this.del_message_ids = del_message_ids;
        this.login = login;
        this.reg_ip = reg_ip;
        this.reg_time = reg_time;
        this.last_login_ip = last_login_ip;
        this.last_login_time = last_login_time;
        this.status = status;
        this.token = token;
        this.pic_url = pic_url;
        this.level_name = level_name;
        this.medal = medal;
        this.level_pic_url = level_pic_url;
        this.next_level_score = next_level_score;
        this.age = age;
    }
    @Generated(hash = 421677132)
    public UserItemEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getOpenid() {
        return this.openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getDevice_token() {
        return this.device_token;
    }
    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getIntro() {
        return this.intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public String getHeight() {
        return this.height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeight() {
        return this.weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getBfr() {
        return this.bfr;
    }
    public void setBfr(String bfr) {
        this.bfr = bfr;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getScore() {
        return this.score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getLevel_id() {
        return this.level_id;
    }
    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }
    public String getLevel_time() {
        return this.level_time;
    }
    public void setLevel_time(String level_time) {
        this.level_time = level_time;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getIs_sure() {
        return this.is_sure;
    }
    public void setIs_sure(String is_sure) {
        this.is_sure = is_sure;
    }
    public String getDumb_time() {
        return this.dumb_time;
    }
    public void setDumb_time(String dumb_time) {
        this.dumb_time = dumb_time;
    }
    public String getIs_push_comments() {
        return this.is_push_comments;
    }
    public void setIs_push_comments(String is_push_comments) {
        this.is_push_comments = is_push_comments;
    }
    public String getIs_push_likes() {
        return this.is_push_likes;
    }
    public void setIs_push_likes(String is_push_likes) {
        this.is_push_likes = is_push_likes;
    }
    public String getRead_message_ids() {
        return this.read_message_ids;
    }
    public void setRead_message_ids(String read_message_ids) {
        this.read_message_ids = read_message_ids;
    }
    public String getDel_message_ids() {
        return this.del_message_ids;
    }
    public void setDel_message_ids(String del_message_ids) {
        this.del_message_ids = del_message_ids;
    }
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getReg_ip() {
        return this.reg_ip;
    }
    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }
    public String getReg_time() {
        return this.reg_time;
    }
    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }
    public String getLast_login_ip() {
        return this.last_login_ip;
    }
    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }
    public String getLast_login_time() {
        return this.last_login_time;
    }
    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getPic_url() {
        return this.pic_url;
    }
    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
    public String getLevel_name() {
        return this.level_name;
    }
    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }
    public String getMedal() {
        return this.medal;
    }
    public void setMedal(String medal) {
        this.medal = medal;
    }
    public String getLevel_pic_url() {
        return this.level_pic_url;
    }
    public void setLevel_pic_url(String level_pic_url) {
        this.level_pic_url = level_pic_url;
    }
    public String getNext_level_score() {
        return this.next_level_score;
    }
    public void setNext_level_score(String next_level_score) {
        this.next_level_score = next_level_score;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
}
