package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-06-07.
 */
public class UserProfileResult {
    int result;
    String message;
    @SerializedName("item")
    UserProfile userProfile;

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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
