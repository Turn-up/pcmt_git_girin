package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LG on 2016-05-25.
 */
public class NoticeList {
    int noticesn;
    String title;
    String content;
    @SerializedName("attachurl")
    String attachUrl;
    @SerializedName("detailattachurl")
    String detailAttachUrl;
    String regdate;

    public int getNoticesn() {
        return noticesn;
    }

    public void setNoticesn(int noticesn) {
        this.noticesn = noticesn;
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

    public String getDetailAttachUrl() {
        return detailAttachUrl;
    }

    public void setDetailAttachUrl(String detailAttachUrl) {
        this.detailAttachUrl = detailAttachUrl;
    }

    public String getRegdate() {
        String toString = "";
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date toDate = transFormat.parse(regdate);
            SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy.MM.dd");
            toString = transFormat2.format(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return toString;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
