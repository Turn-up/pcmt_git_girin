package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-06-10.
 */
public class PointListResult {
    int result;
    String message;
    int totalcount;
    @SerializedName("remainmileage")
    int remainMileage;

    @SerializedName("list")
    List<PointList> pointList;
    public List<PointList> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointList> pointList) {
        this.pointList = pointList;
    }

    public int getRemainMileage() {
        return remainMileage;
    }

    public void setRemainMileage(int remainMileage) {
        this.remainMileage = remainMileage;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
