package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-20.
 */
public class ExpertDetailInfo {
    String expertname;
    String photo;
    @SerializedName("mainintroduce")
    String mainIntroduce;
    @SerializedName("detailintroduce")
    String detailIntroduce;
    @SerializedName("officename")
    String officeName;
    String homepage;
    String tel;
    String email;
    String address;//번지주소
    String longitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String latitude;
    String license;
    int career;
    String sex;
    String belongto;
    String age;
    List<ExpertDetailCategory> categorys;
    List<ExpertDetailRecord> record;
    List<ExpertDetailAcademic> academic;
    public List<ExpertDetailAcademic> getAcademic() {
        return academic;
    }



    public List<ExpertDetailCategory> getCategory() {
        return categorys;
    }

    public void setCategory(List<ExpertDetailCategory> category) {
        this.categorys = category;
    }

    public List<ExpertDetailRecord> getRecord() {
        return record;
    }

    public void setRecord(List<ExpertDetailRecord> record) {
        this.record = record;
    }

    public void setAcademic(List<ExpertDetailAcademic> academic) {
        this.academic = academic;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }


    public int getCareer() {
        return career;
    }

    public void setCareer(int career) {
        this.career = career;
    }


    public String getName() {
        return expertname;
    }

    public void setName(String name) {
        this.expertname = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMainIntroduce() {
        return mainIntroduce;
    }

    public void setMainIntroduce(String mainIntroduce) {
        this.mainIntroduce = mainIntroduce;
    }

    public String getDetailIntroduce() {
        return detailIntroduce;
    }

    public void setDetailIntroduce(String detailIntroduce) {
        this.detailIntroduce = detailIntroduce;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBelongto() {
        return belongto;
    }

    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
