package com.baogetv.app.bean;

/**
 * Created by chalilayang on 2017/12/3.
 */

public class SameFileUploadBean {

    /**
     * status : 1
     * msg : 上传成功
     * url :
     * data : {"id":"6","path":"http://localhost/test2/Uploads/Picture/2016-12-21
     * /585a18a4d85d5.png","url":"","md5":"2d0ef89a75042f52210be3f111fbbc83",
     * "sha1":"957820b04576052f3f69a48424d7326a644a6f8d","status":"1","create_time":"1482299555"}
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
         * id : 6
         * path : http://localhost/test2/Uploads/Picture/2016-12-21/585a18a4d85d5.png
         * url :
         * md5 : 2d0ef89a75042f52210be3f111fbbc83
         * sha1 : 957820b04576052f3f69a48424d7326a644a6f8d
         * status : 1
         * create_time : 1482299555
         */

        private String id;
        private String path;
        private String url;
        private String md5;
        private String sha1;
        private String status;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
