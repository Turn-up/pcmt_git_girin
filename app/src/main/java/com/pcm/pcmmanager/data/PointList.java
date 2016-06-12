package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-06-10.
 */
public class PointList {
    int mileagelogsn;

    public int getMileagelogsn() {
        return mileagelogsn;
    }

    public void setMileagelogsn(int mileagelogsn) {
        this.mileagelogsn = mileagelogsn;
    }

    public String getMileagetype() {
        return mileagetype;
    }

    public void setMileagetype(String mileagetype) {
        this.mileagetype = mileagetype;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    String mileagetype;
    int mileage;
    String regdate;
}
