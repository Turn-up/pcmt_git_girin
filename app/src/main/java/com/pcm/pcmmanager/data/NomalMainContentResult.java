package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-05-30.
 */
public class NomalMainContentResult {
    int result;
    String massage;
    NomalMainContent item;
    public NomalMainContent getItem() {
        return item;
    }

    public void setItem(NomalMainContent item) {
        this.item = item;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
