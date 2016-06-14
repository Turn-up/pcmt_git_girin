package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-27.
 */
public class ExpertNavInfo {
    @SerializedName("expertsn")
    int expertSn;

    @SerializedName("expertname")
    String expertName;
    @SerializedName("bidcount")
    int entryCount;
    @SerializedName("successbidcount")
    int bidSuccessCount;
    @SerializedName("bidendcount")
    int bidFinishCount;
    @SerializedName("email")
    String expertEmail;
    @SerializedName("photo")
    String expertPhoto;
    int mileage;
    String sex;

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getExpertPhoto() {
        return expertPhoto;
    }

    public void setExpertPhoto(String expertPhoto) {
        this.expertPhoto = expertPhoto;
    }

    public String getExpertEmail() {
        return expertEmail;
    }

    public void setExpertEmail(String expertEmail) {
        this.expertEmail = expertEmail;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }


    public int getBidFinishCount() {
        return bidFinishCount;
    }

    public void setBidFinishCount(int bidFinishCount) {
        this.bidFinishCount = bidFinishCount;
    }

    public int getBidSuccessCount() {
        return bidSuccessCount;
    }

    public void setBidSuccessCount(int bidSuccessCount) {
        this.bidSuccessCount = bidSuccessCount;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
    public int getExpertSn() {
        return expertSn;
    }

    public void setExpertSn(int expertSn) {
        this.expertSn = expertSn;
    }

}
