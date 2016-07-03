package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-05-24.
 */
public class ExpertEstimateList {

    @SerializedName("marketsn")
    private int marketSn;
    @SerializedName("usersn")
    private int userSn;
    @SerializedName("username")
    private String userName;
    @SerializedName("markettype")
    private String marketType;
    @SerializedName("marketsubtype")
    private String marketTypeSub;
    @SerializedName("markettype1_2")
    private long businessScale;
    @SerializedName("markettype1_4")
    private String employeeCount;
    @SerializedName("markettype2_1")
    List<String> taxDetail;
    @SerializedName("markettype2_2")
    List<Long> marketPrice;
    private String enddate;
    private String content;
    private List<MainBidCount> bids;

    public List<String> getAssetType() {
        return taxDetail;
    }

    public void setTaxDetail(List<String> taxDetail) {
        this.taxDetail = taxDetail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMarketSubType() {
        return marketTypeSub;
    }

    public void setMarketTypeSub(String marketTypeSub) {
        this.marketTypeSub = marketTypeSub;
    }

    public String getBusinessScale() {
        String bs;
        if (businessScale < 1000000) {
            bs = businessScale / 1000 + "만원";
        } else if (1000000 <= businessScale && businessScale < 100000000) {
            bs = businessScale / 10000 + "만원";
        } else {
            bs = businessScale / 100000000 + "." + (businessScale % 100000000) / 10000000 + "억원";
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

    public List<String> getMarketPrice() {
        List<String> bs = new ArrayList<String>();
        for(int i=0; i<marketPrice.size(); i++) {
            if (marketPrice.get(i) < 1000000) {
                bs.add(marketPrice.get(i) / 1000 + "만원");
            } else if (1000000 <= marketPrice.get(i) && marketPrice.get(i) < 100000000) {
                bs.add(marketPrice.get(i) / 10000 + "만원");
            } else {
                bs.add(marketPrice.get(i) / 100000000 + "." + (marketPrice.get(i) % 100000000) / 10000000 + "억원");
            }
        }
        return bs;
    }

    public void setMarketPrice(List<Long> marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getBidscount() {
        return bids.size() ;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getUserName() {
        return userName;
    }

    public String getMarketType() {
        return marketType;
    }

    public String getEnddate() {
        return enddate;
    }

    public int getMarketSn() {
        return marketSn;
    }

    public void setMarketSn(int marketSn) {
        this.marketSn = marketSn;
    }

    public int getUserSn() {
        return userSn;
    }

    public void setUserSn(int userSn) {
        this.userSn = userSn;
    }

}



