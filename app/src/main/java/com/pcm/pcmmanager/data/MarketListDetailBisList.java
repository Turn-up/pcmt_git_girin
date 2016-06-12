package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-19.
 */
public class MarketListDetailBisList {
    int bidsn;
    int expertsn;
    @SerializedName("expertname")
    String expertName;

    @SerializedName("photo")
    String expertPhoto;

    @SerializedName("price1")
    int monthPrice;

    @SerializedName("price2")
    int modifyPrice;

    String comment;
    @SerializedName("regdate")
    String regDate;



    public int getBidsn() {
        return bidsn;
    }

    public void setBidsn(int bidsn) {
        this.bidsn = bidsn;
    }

    public int getExpertsn() {
        return expertsn;
    }

    public void setExpertsn(int expertsn) {
        this.expertsn = expertsn;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getExpertPhoto() {
        return expertPhoto;
    }

    public void setExpertPhoto(String expertPhoto) {
        this.expertPhoto = expertPhoto;
    }

    public int getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(int monthPrice) {
        this.monthPrice = monthPrice;
    }

    public int getModifyPrice() {
        return modifyPrice;
    }

    public void setModifyPrice(int modifyPrice) {
        this.modifyPrice = modifyPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

}
