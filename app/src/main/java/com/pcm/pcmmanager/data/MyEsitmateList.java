package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LG on 2016-05-18.
 */
public class MyEsitmateList {
    private String _id;
    @SerializedName("usersn")
    private String userSn;
    @SerializedName("username")
    private String userName;
    @SerializedName("marketsn")
    private int marketSn;
    @SerializedName("markettype")
    private String marketType;
    @SerializedName("marketsubtype")
    private String marketSubType;
    @SerializedName("markettype1_1")
    private String markettype1_1; //기존 , 신규
    @SerializedName("markettype1_2")
    private long businessScale;
    @SerializedName("markettype1_3")
    private String markettype1_3;
    @SerializedName("markettype1_4")
    private String employeeCount;
    @SerializedName("markettype2_1")
    List<String> assetType;
    @SerializedName("markettype2_2")
    private long marketPrice;
    private int bidscount = 0;
    @SerializedName("enddate")
    private String endDate;
    @SerializedName("regdate")
    private String regDate;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarkettype1_1() {
        return markettype1_1;
    }

    public void setMarkettype1_1(String markettype1_1) {
        this.markettype1_1 = markettype1_1;
    }

    public String getMarkettype1_3() {
        return markettype1_3;
    }

    public void setMarkettype1_3(String markettype1_3) {
        this.markettype1_3 = markettype1_3;
    }

    public List<String> getAssetType() {
        return assetType;
    }

    public void setAssetType(List<String> assetType) {
        this.assetType = assetType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getRegDate() {

        long now = System.currentTimeMillis();// 시스템으로부터 현재시간(ms) 가져오기
        long dateResult = 0;
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date toDate = transFormat.parse(regDate);
            Date nowDate = new Date(now); // Data 객체에 시간을 저장한다.
            String strNowDate =  transFormat.format(nowDate);
            nowDate = transFormat.parse(strNowDate);
            dateResult = nowDate.getTime() - toDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateResult;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUserSn() {

        return userSn;
    }

    public void setUserSn(String userSn) {
        this.userSn = userSn;
    }

    public int getMarketSn() {
        return marketSn;
    }

    public void setMarketSn(int marketSn) {
        this.marketSn = marketSn;
    }


    public String getMarketSubType() {
        return marketSubType;
    }

    public void setMarketSubType(String marketSubType) {
        this.marketSubType = marketSubType;
    }


    public String getBusinessScale() {
        String bs;
        if (businessScale < 1000000) {
            bs = businessScale / 1000 + "만원";
        } else if (1000000 <= businessScale && businessScale < 100000000) {
            bs = businessScale / 100000 + "만원";
        } else {
            bs = businessScale / 10000000 + "억";
        }
        return bs;
    }

    public void setBusinessScale(long businessScale) {
        this.businessScale = businessScale;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getMarketPrice() {
        String bs;
        if (marketPrice < 1000000) {
            bs = marketPrice / 1000 + "만원";
        } else if (1000000 <= marketPrice && marketPrice < 100000000) {
            bs = marketPrice / 100000 + "만원";
        } else {
            bs = marketPrice / 10000000 + "억";
        }
        return bs;
    }

    public void setMarketPrice(long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getBidscount() {
        return bidscount;
    }

    public void setBidscount(int bidscount) {
        this.bidscount = bidscount;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public void setBidsCount(int oldCnt) {
        this.bidscount = oldCnt;
    }


    public String get_id() {
        return _id;
    }


    public String getUserName() {
        return userName;
    }

    public String getMarketType() {
        return marketType;
    }

    public int getOldCnt() {
        return bidscount;
    }

}
