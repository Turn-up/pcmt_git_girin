package com.pcm.pcmmanager.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailReviewList {
    String _id;
    int expertsn;
    String expertname;
    String sex;
    String photo;
    String license;
    String mainintroduce;
    String officename;
    int career;
    int age;
    String content;
    int likecount;
    boolean likeyn;
    String regdate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getExpertsn() {
        return expertsn;
    }

    public void setExpertsn(int expertsn) {
        this.expertsn = expertsn;
    }

    public String getExpertname() {
        return expertname;
    }

    public void setExpertname(String expertname) {
        this.expertname = expertname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMainintroduce() {
        return mainintroduce;
    }

    public void setMainintroduce(String mainintroduce) {
        this.mainintroduce = mainintroduce;
    }

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public int getCareer() {
        return career;
    }

    public void setCareer(int career) {
        this.career = career;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public boolean isLikeyn() {
        return likeyn;
    }

    public void setLikeyn(boolean likeyn) {
        this.likeyn = likeyn;
    }

    public String getRegdate() {
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy.MM.dd");
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
}
