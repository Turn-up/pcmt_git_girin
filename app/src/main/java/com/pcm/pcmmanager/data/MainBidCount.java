package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-28.
 */
public class MainBidCount {
    @SerializedName("bidscount")
    int bidCount;
    @SerializedName("bidsendcount")
    int bidEndCount;

    public int getBidEndCount() {
        return bidEndCount;
    }

    public void setBidEndCount(int bidEndCount) {
        this.bidEndCount = bidEndCount;
    }

    public int getBidCount() {

        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }
}
