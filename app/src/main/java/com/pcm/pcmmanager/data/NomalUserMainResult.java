package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-06-07.
 */
public class NomalUserMainResult {
    int result;
    String message;
    @SerializedName("item")
    NomalUserMain nomalUserMain;

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

    public NomalUserMain getNomalUserMain() {
        return nomalUserMain;
    }

    public void setNomalUserMain(NomalUserMain nomalUserMain) {
        this.nomalUserMain = nomalUserMain;
    }
}
