package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-20.
 */
public class ExpertDetailResult {
    int result;
    String message;
    @SerializedName("item")
    ExpertDetailInfo info;


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExpertDetailInfo getInfo() {
        return info;
    }

    public void setInfo(ExpertDetailInfo info) {
        this.info = info;
    }

}
