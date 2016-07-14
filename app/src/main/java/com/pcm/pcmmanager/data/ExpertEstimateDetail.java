package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LG on 2016-05-30.
 */
public class ExpertEstimateDetail {
    String _id;
    int usersn;
    String username;
    Boolean reviewyn;
    @SerializedName("markettype")
    String marketType;
    @SerializedName("marketsubtype")
    String marketSubType;
    @SerializedName("regiontype")
    String address1;
    @SerializedName("regionsubtype")
    String address2;
    String markettype1_3;
    @SerializedName("markettype2_2")
    List<Long> assetMoney;
    List<ExpertEstimateDetailBidList> bids;
    String content;
    String success_bid_id; //낙찰일련번호
    String status;
    @SerializedName("regdate")
    String regDate;
    @SerializedName("enddate")
    String enddate;
    @SerializedName("markettype1_2")
    long businessScale;
    int success_expertsn;
    @SerializedName("markettype1_4")
    int employeeCount;
    @SerializedName("markettype2_1")
    List<String> assetType;
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMarkettpye1_3() {
        return markettype1_3;
    }

    public void setMarkettpye1_3(String markettpye1_3) {
        this.markettype1_3 = markettpye1_3;
    }

    public int getSuccess_expertsn() {
        return success_expertsn;
    }

    public void setSuccess_expertsn(int success_expertsn) {
        this.success_expertsn = success_expertsn;
    }
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
            bs = businessScale / 1000 + "만원";
        } else if (1000000 <= businessScale && businessScale < 100000000) {
            bs = businessScale / 10000 + "만";
        } else {
            bs = businessScale / 100000000 + "." + (businessScale % 100000000) / 10000000 + "억";
        }
        return bs;
    }

    public long getNumberBusinessScale() {
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

    public List<Long> getNumberAssetMoney() {
        return assetMoney;
    }

    public List<String> getAssetMoney() {
        List<String> bs = new ArrayList<String>();
        for (int i = 0; i < assetMoney.size(); i++) {
            if (assetMoney.get(i) < 1000000) {
                bs.add(assetMoney.get(i) / 10000 + "만원");
            } else if (1000000 <= assetMoney.get(i) && assetMoney.get(i) < 100000000) {
                bs.add(assetMoney.get(i) / 10000 + "만원");
            } else {
                bs.add(assetMoney.get(i) / 100000000 + "." + (assetMoney.get(i) % 100000000) / 10000000 + "억원");
            }
        }

        return bs;
    }

    public void setAssetMoney(List<Long> assetMoney) {
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

    public List<String> getAssetType() {
        return assetType;
    }

    public void setAssetType(List<String> assetType) {
        this.assetType = assetType;
    }

    public boolean getReviewyn() {
        return reviewyn;
    }

    public void setReviewyn(boolean reviewyn) {
        this.reviewyn = reviewyn;
    }
}
