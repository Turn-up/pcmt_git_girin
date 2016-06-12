package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-28.
 */
public class MainBidCountResult {
    int result;
    String maessage;
    @SerializedName("item")
    MainBidCount mainBidCount;

    public MainBidCount getMainBidCount() {
        return mainBidCount;
    }

    public void setMainBidCount(MainBidCount mainBidCount) {
        this.mainBidCount = mainBidCount;
    }

    public String getMaessage() {
        return maessage;
    }

    public void setMaessage(String maessage) {
        this.maessage = maessage;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
