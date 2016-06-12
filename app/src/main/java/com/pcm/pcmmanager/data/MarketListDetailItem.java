package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-19.
 */
public class MarketListDetailItem {
    int usersn;
    @SerializedName("username")
    String userName;
    @SerializedName("markettype")
    String marketType;
    @SerializedName("marketsubtype")
    String marketsubtype;
    String address1;
    String address2;
    @SerializedName("markettype1_2")
    int saleScale;
    @SerializedName("markettype1_3")
    String businessType;
    @SerializedName("markettype1_4")
    int workerCount;
    @SerializedName("markettype2_1")
    String assetTarget;
    @SerializedName("markettype2_2")
    int assetMoney;
    @SerializedName("bidscount")
    int bidsCount;

    String content;
    String success_bidsn;
    String status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuccess_bidsn() {
        return success_bidsn;
    }

    public void setSuccess_bidsn(String success_bidsn) {
        this.success_bidsn = success_bidsn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @SerializedName("regdate")
    String regDate;

    @SerializedName("enddate")
    String endDate;

    @SerializedName("bids")
    List<MarketListDetailBisList> bidList;



    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getMarketsubtype() {
        return marketsubtype;
    }

    public void setMarketsubtype(String marketsubtype) {
        this.marketsubtype = marketsubtype;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getSaleScale() {
        return saleScale;
    }

    public void setSaleScale(int saleScale) {
        this.saleScale = saleScale;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount = workerCount;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAssetTarget() {
        return assetTarget;
    }

    public void setAssetTarget(String assetTarget) {
        this.assetTarget = assetTarget;
    }

    public int getAssetMoney() {
        return assetMoney;
    }

    public void setAssetMoney(int assetMoney) {
        this.assetMoney = assetMoney;
    }

    public int getBidsCount() {
        return bidsCount;
    }

    public void setBidsCount(int bidsCount) {
        this.bidsCount = bidsCount;
    }

    public List<MarketListDetailBisList> getBidList() {
        return bidList;
    }

    public void setBidList(List<MarketListDetailBisList> bidList) {
        this.bidList = bidList;
    }

}
