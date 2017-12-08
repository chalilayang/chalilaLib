package com.baogetv.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chalilayang on 2017/11/26.
 */

public class GradeBean implements Parcelable {

    /**
     * id : 1
     * name : LV1
     * medal :
     * score : 30
     * pic_url :
     * status : 1
     * add_time : 2017-10-23 20:37:30
     * pic : 0
     */

    private String id;
    private String name;
    private String medal;
    private String score;
    private String pic_url;
    private String status;
    private String add_time;
    private String pic;

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

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.medal);
        dest.writeString(this.score);
        dest.writeString(this.pic_url);
        dest.writeString(this.status);
        dest.writeString(this.add_time);
        dest.writeString(this.pic);
    }

    public GradeBean() {
    }

    protected GradeBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.medal = in.readString();
        this.score = in.readString();
        this.pic_url = in.readString();
        this.status = in.readString();
        this.add_time = in.readString();
        this.pic = in.readString();
    }

    public static final Creator<GradeBean> CREATOR = new Creator<GradeBean>() {
        @Override
        public GradeBean createFromParcel(Parcel source) {
            return new GradeBean(source);
        }

        @Override
        public GradeBean[] newArray(int size) {
            return new GradeBean[size];
        }
    };
}
