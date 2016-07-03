package com.pcm.pcmmanager.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LG on 2016-06-27.
 */
public class QnaList {
    int qnasn;
    int usersn;
    String username;
    String title;
    int commentcount;
    Boolean secretyn;
    String regdate;
    String content;

    public int getQnasn() {
        return qnasn;
    }

    public void setQnasn(int qnasn) {
        this.qnasn = qnasn;
    }

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

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public Boolean getSecretyn() {
        return secretyn;
    }

    public void setSecretyn(Boolean secretyn) {
        this.secretyn = secretyn;
    }

    public String getRegdate() {
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date original_date = original_format.parse(regdate);
            regdate = new_format.format(original_date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
