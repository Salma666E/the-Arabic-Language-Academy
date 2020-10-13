package com.example.magmaa.pages.GeneralMeeting;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GMeetingCatResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("session_num")
    @Expose
    private Integer sessionNum;
    @SerializedName("course_num")
    @Expose
    private Integer courseNum;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSessionNum() {
        return sessionNum;
    }

    public void setSessionNum(Integer sessionNum) {
        this.sessionNum = sessionNum;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}
