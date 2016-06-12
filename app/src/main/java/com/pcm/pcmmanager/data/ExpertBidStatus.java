package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-28.
 */
public class ExpertBidStatus {
    int usersn;
    @SerializedName("usersname")
    String usersName;
    @SerializedName("marketsn")
    int marketSn;
    @SerializedName("markettype")
    String marketType;
    @SerializedName("marketsubtype")
    String marketSubtype;
    @SerializedName("markettype1_2")
    long businessScale;
    @SerializedName("markettype1_4")
    int employeeCount;
    @SerializedName("markettype2_1")
    List<String> assetType;

    public List<String> getAssetType() {
        return assetType;
    }

    public void setAssetType(List<String> assetType) {
        this.assetType = assetType;
    }

    @SerializedName("markettype2_2")
    long marketPrice;
    @SerializedName("bidscount")
    int bidCount;
    @SerializedName("regdate")
    String regDate;
    @SerializedName("enddate")
    String endDate;
    int success_expertsn;
    int price1;
    int price2;
    String phone;
    String status;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public String getMarketPrice() {
        String bs;
        if (marketPrice < 1000000) {
            bs = marketPrice/1000 +"만원";
        } else if (1000000 <= marketPrice && marketPrice < 100000000) {
            bs = marketPrice / 100000 + "만원";
        } else {
            bs = marketPrice / 10000000 + "억";
        }
        return bs;
    }

    public void setMarketPrice(long marketMoney) {
        this.marketPrice = marketMoney;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getBusinessScale() {
        String bs;
        if (businessScale < 1000000) {
            bs = businessScale/1000 +"만원";
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

    public String getMarketSubtype() {
        return marketSubtype;
    }

    public void setMarketSubtype(String marketSubtype) {
        this.marketSubtype = marketSubtype;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public int getMarketSn() {
        return marketSn;
    }

    public void setMarketSn(int marketSn) {
        this.marketSn = marketSn;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public int getUsersn() {
        return usersn;
    }

    public void setUsersn(int usersn) {
        this.usersn = usersn;
    }

}
