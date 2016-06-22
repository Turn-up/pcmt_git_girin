package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-06-21.
 */
public class PersonalInfoSearchResult {
    int result;
    String message;
    @SerializedName("item")
    PersonalInfoSearch personalInfoSearch;

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

    public PersonalInfoSearch getPersonalInfoSearch() {
        return personalInfoSearch;
    }

    public void setPersonalInfoSearch(PersonalInfoSearch personalInfoSearch) {
        this.personalInfoSearch = personalInfoSearch;
    }
}
