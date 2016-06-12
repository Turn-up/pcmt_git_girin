package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-30.
 */
public class ExpertEstimateDetail {
    String _id;
    int usersn;
    String username;
    String reviewsn;
    @SerializedName("markettype")
    String marketType;
    @SerializedName("marketsubtype")
    String marketSubType;
    @SerializedName("regiontype")
    String address1;
    @SerializedName("regionsubtype")
    String address2;
    String markettype1_3;

    public String getMarkettpye1_3() {
        return markettype1_3;
    }

    public void setMarkettpye1_3(String markettpye1_3) {
        this.markettype1_3 = markettpye1_3;
    }

    @SerializedName("markettype1_2")
    long businessScale;
    int success_expertsn;
    @SerializedName("markettype1_4")
    int employeeCount;
    @SerializedName("markettype2_1")
    List<String> asset_type;

    public int getSuccess_expertsn() {
        return success_expertsn;
    }

    public void setSuccess_expertsn(int success_expertsn) {
        this.success_expertsn = success_expertsn;
    }

    @SerializedName("markettype2_2")
    long assetMoney;
    List<ExpertEstimateDetailBidList> bids;
    String content;
    String success_bid_id; //낙찰일련번호
    String status;
    @SerializedName("regdate")
    String regDate;
    @SerializedName("enddate")
    String enddate;

    public int getUsersn() {
        return usersn;
    }

    public void setUsersn(int usersn) {
        this.usersn = usersn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getMarketSubType() {
        return marketSubType;
    }

    public void setMarketSubType(String marketSubType) {
        this.marketSubType = marketSubType;
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
    public long getNumberBusinessScale(){
        return businessScale;
    }
    public void setBusinessScale(long businessScale) {
        this.businessScale = businessScale;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public long getNumberAssetMoney(){
        return assetMoney;
    }
    public String getAssetMoney() {
        String bs;
        if (assetMoney < 1000000) {
            bs = assetMoney/1000 +"만원";
        } else if (1000000 <= assetMoney && assetMoney < 100000000) {
            bs = assetMoney / 100000 + "만원";
        } else {
            bs = assetMoney / 10000000 + "억";
        }
        return bs;
    }

    public void setAssetMoney(long assetMoney) {
        this.assetMoney = assetMoney;
    }

    public int getBidCount() {
        return bids.size();
    }

    public List<ExpertEstimateDetailBidList> getBids() {
        return bids;
    }

    public void setBids(List<ExpertEstimateDetailBidList> bids) {
        this.bids = bids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuccess_bid_id() {
        return success_bid_id;
    }

    public void setSuccess_bid_id(String success_bid_id) {
        this.success_bid_id = success_bid_id;
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

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(List<String> asset_type) {
        this.asset_type = asset_type;
    }
    public String getReviewsn() {
        return reviewsn;
    }

    public void setReviewsn(String reviewsn) {
        this.reviewsn = reviewsn;
    }
}
