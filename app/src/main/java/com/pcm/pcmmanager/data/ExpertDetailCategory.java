package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-24.
 */
public class ExpertDetailCategory {
    @SerializedName("markettype")
    String marketType;
    @SerializedName("marketsubtype")
    String marketSubType;

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getMarketSubType() {
        return marketSubType;
    }

    public void setMarketSubType(String marketSubType) {
        this.marketSubType = marketSubType;
    }
}
