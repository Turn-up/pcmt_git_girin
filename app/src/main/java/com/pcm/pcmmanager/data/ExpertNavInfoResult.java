package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-27.
 */
public class ExpertNavInfoResult {

    int result;
    String message;
    @SerializedName("item")
    ExpertNavInfo expertNavInfo;

    public ExpertNavInfo getExpertNavInfo() {
        return expertNavInfo;
    }

    public void setExpertNavInfo(ExpertNavInfo expertNavInfo) {
        this.expertNavInfo = expertNavInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
