package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-07-11.
 */
public class UserInfoModifyResult {
    int result;
    String message;
    String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
