package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LG on 2016-05-30.
 */
public class ExpertDetailReview {
    @SerializedName("marketsn")
    int marketSn;
    @SerializedName("usersn")
    int userSn;
    @SerializedName("username")
    String userName;
    double score;
    String content;
    @SerializedName("regdate")
    String regDate;

    public int getMarketSn() {
        return marketSn;
    }

    public void setMarketSn(int marketSn) {
        this.marketSn = marketSn;
    }

    public int getUserSn() {
        return userSn;
    }

    public void setUserSn(int userSn) {
        this.userSn = userSn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegDate() {
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date toDate = transFormat.parse(regDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String regDate = sdf.format(toDate);
            return regDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return regDate;
        }
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
