
package com.example.magmaa.Comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCommentResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("arabic__word_id")
    @Expose
    private String arabicWordId;
    @SerializedName("word_id")
    @Expose
    private Integer wordId;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;


    public AllCommentResponse( String comment , int userId , String arabicWordId ){
        this.comment= comment;
        this.userId= userId;
        this.arabicWordId= arabicWordId;
    }
    public AllCommentResponse( String comment , int userId , int wordId ){
        this.comment= comment;
        this.userId= userId;
        this.wordId= wordId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getArabicWordId() {
        return arabicWordId;
    }

    public void setArabicWordId(String arabicWordId) {
        this.arabicWordId = arabicWordId;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
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
