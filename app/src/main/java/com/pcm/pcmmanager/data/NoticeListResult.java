package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-25.
 */
public class NoticeListResult {
    int result;
    String message;
    @SerializedName("totalcount")
    int totalCount;
    @SerializedName("list")
    List<NoticeList> noticeList;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<NoticeList> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeList> noticeList) {
        this.noticeList = noticeList;
    }
}
