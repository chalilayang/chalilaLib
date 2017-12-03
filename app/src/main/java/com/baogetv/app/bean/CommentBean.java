package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/11/27.
 */

public class CommentBean {

    /**
     * video_id : 1
     * pic_url : http://localhost/test2/Uploads/Picture/2016-12-05/58451c922375d.png
     * title : 测试
     * type_name : 频道1
     * username : null
     * userpic : null
     * id : 2
     * content : 内容
     * add_time : 2017-11-12 09:16:20
     * pic : 1
     * is_collect : 0
     * is_like : 0
     */

    private String video_id;
    private String pic_url;
    private String title;
    private String type_name;
    private Object username;
    private Object userpic;
    private String id;
    private String content;
    private String add_time;
    private String pic;
    private String is_collect;
    private String is_like;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getUserpic() {
        return userpic;
    }

    public void setUserpic(Object userpic) {
        this.userpic = userpic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }
}
