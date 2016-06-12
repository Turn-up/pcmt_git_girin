package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-30.
 */
public class NomalMainContentTipList {
    @SerializedName("tipsn")
    int tipSn;
    @SerializedName("linkurl")
    String linkUrl;
    @SerializedName("attachurl")
    String attachUrl;

    public int getTipSn() {
        return tipSn;
    }

    public void setTipSn(int tipSn) {
        this.tipSn = tipSn;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }
}
