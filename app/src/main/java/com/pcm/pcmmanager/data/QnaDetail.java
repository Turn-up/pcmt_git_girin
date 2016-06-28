package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetail {
    int usersn;
    String username;
    String title;
    String content;
    boolean secretyn;
    String regdate;
    @SerializedName("comments")
    List<QnaDetailReviewList> qnaDetailReviewList;

    public int getUsersn() {
        return usersn;
    }

    public void setUsersn(int usersn) {
        this.usersn = usersn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isSecretyn() {
        return secretyn;
    }

    public void setSecretyn(boolean secretyn) {
        this.secretyn = secretyn;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public List<QnaDetailReviewList> getQnaDetailReviewList() {
        return qnaDetailReviewList;
    }

    public void setQnaDetailReviewList(List<QnaDetailReviewList> qnaDetailReviewList) {
        this.qnaDetailReviewList = qnaDetailReviewList;
    }
}
