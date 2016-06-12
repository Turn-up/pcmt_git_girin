package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-30.
 */
public class NomalMainContent {
    @SerializedName("tipslist")
    List<NomalMainContentTipList> tipList;
    @SerializedName("noticeslist")
    List<NomalMainContentNoticeList> noticeList;

    public List<NomalMainContentTipList> getTipList() {
        return tipList;
    }

    public void setTipList(List<NomalMainContentTipList> tipList) {
        this.tipList = tipList;
    }

    public List<NomalMainContentNoticeList> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NomalMainContentNoticeList> noticeList) {
        this.noticeList = noticeList;
    }
}
