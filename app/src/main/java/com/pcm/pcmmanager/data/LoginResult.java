package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-06-09.
 */
public class LoginResult {
    int result;
    String message;
    String token;
    String roles;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    public int getResult() {

        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
