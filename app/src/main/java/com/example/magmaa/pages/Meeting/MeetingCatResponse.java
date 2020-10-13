package com.example.magmaa.pages.Meeting;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeetingCatResponse  {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("session_num")
    @Expose
    private String sessionNum;
    @SerializedName("course_num")
    @Expose
    private String courseNum;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("commission_id")
    @Expose
    private String commissionId;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionNum() {
        return sessionNum;
    }

    public void setSessionNum(String sessionNum) {
        this.sessionNum = sessionNum;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(String commissionId) {
        this.commissionId = commissionId;
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
