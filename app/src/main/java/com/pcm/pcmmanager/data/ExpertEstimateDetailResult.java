package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-05-30.
 */
public class ExpertEstimateDetailResult {
    int result;
    String message;
    ExpertEstimateDetail item;

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

    public ExpertEstimateDetail getItem() {
        return item;
    }

    public void setItem(ExpertEstimateDetail item) {
        this.item = item;
    }
}
