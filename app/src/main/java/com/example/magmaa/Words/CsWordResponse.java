
package com.example.magmaa.Words;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CsWordResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("meeting_id")
    @Expose
    private Integer meetingId;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("commission")
    @Expose
    private String commission;
    @SerializedName("arabic_word")
    @Expose
    private String arabicWord;
    @SerializedName("english_word")
    @Expose
    private String englishWord;
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

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getArabicWord() {
        return arabicWord;
    }

    public void setArabicWord(String arabicWord) {
        this.arabicWord = arabicWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
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
