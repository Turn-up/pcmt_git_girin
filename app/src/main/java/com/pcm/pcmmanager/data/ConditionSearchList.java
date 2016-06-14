package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-25.
 */
public class ConditionSearchList {

    int expertsn;
    @SerializedName("expertname")
    String expertName;
    @SerializedName("officename")
    String officeName;
    String career;
    @SerializedName("mainintroduce")
    String mainIntroduce;
    @SerializedName("photo")
    String photoUrl;
    int age;
    String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getExpertSn() {
        return expertsn;
    }

    public void setExpertSn(int expertsn) {
        this.expertsn = expertsn;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getMainIntroduce() {
        return mainIntroduce;
    }

    public void setMainIntroduce(String mainIntroduce) {
        this.mainIntroduce = mainIntroduce;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
