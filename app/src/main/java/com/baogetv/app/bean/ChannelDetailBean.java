package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/11/25.
 */

public class ChannelDetailBean {

    /**
     * status : 1
     * msg :
     * url :
     * data : {"id":"1","name":"频道1","intro":"","pic_url":"","cover_url":"","sort":"0",
     * "count":"0","is_commend":"0","add_time":"1970-01-01 08:00:00","update_time":"1970-01-01
     * 08:00:00","status":"1","pic":"0","cover":"0"}
     */

    private int status;
    private String msg;
    private String url;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 频道1
         * intro :
         * pic_url :
         * cover_url :
         * sort : 0
         * count : 0
         * is_commend : 0
         * add_time : 1970-01-01 08:00:00
         * update_time : 1970-01-01 08:00:00
         * status : 1
         * pic : 0
         * cover : 0
         */

        private String id;
        private String name;
        private String intro;
        private String pic_url;
        private String cover_url;
        private String sort;
        private String count;
        private String is_commend;
        private String add_time;
        private String update_time;
        private String status;
        private String pic;
        private String cover;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getIs_commend() {
            return is_commend;
        }

        public void setIs_commend(String is_commend) {
            this.is_commend = is_commend;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
