package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-30.
 */
public class NomalMainContentNoticeList {
    @SerializedName("noticesn")
    int noticeSn;
    String title;
    String content;
    @SerializedName("attachurl")
    String attachUrl;

    public int getNoticeSn() {
        return noticeSn;
    }

    public void setNoticeSn(int noticeSn) {
        this.noticeSn = noticeSn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getDetailAtachUrl() {
        return detailAtachUrl;
    }

    public void setDetailAtachUrl(String detailAtachUrl) {
        this.detailAtachUrl = detailAtachUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @SerializedName("detailattachurl")
    String detailAtachUrl;
    String status;
    @SerializedName("regdate")
    String regDate;
    }
