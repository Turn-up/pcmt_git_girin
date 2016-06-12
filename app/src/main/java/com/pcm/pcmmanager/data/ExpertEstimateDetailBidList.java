package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-05-30.
 */
public class ExpertEstimateDetailBidList {
    String _id; //입찰일련번호
    @SerializedName("expertsn")
    int expertSn;
    @SerializedName("expertname")
    String expertName;
    int price1;
    int price2;
    @SerializedName("mainintroduce")
    String mainIntroduce;
    String photo;
    @SerializedName("regdate")
    String regDate;
    String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getExpertSn() {
        return expertSn;
    }

    public void setExpertSn(int expertSn) {
        this.expertSn = expertSn;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public int getPrice() {
        return price1;
    }

    public void setPrice(int price) {
        this.price1 = price;
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public String getMainIntroduce() {
        return mainIntroduce;
    }

    public void setMainIntroduce(String mainIntroduce) {
        this.mainIntroduce = mainIntroduce;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
