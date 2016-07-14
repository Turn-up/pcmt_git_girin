package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @SerializedName("markettype2_2")
    List<Long> marketPrice;
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

    public List<String> getAssetType() {
        return assetType;
    }

    public void setAssetType(List<String> assetType) {
        this.assetType = assetType;
    }


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


    public String getStringRegdate() {
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat new_format = new SimpleDateFormat("yy.MM.dd");
        try {
            Date original_date = original_format.parse(regDate);
            regDate = new_format.format(original_date);
            Date regdate = new_format.parse(regDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(regdate);
            int cal_add = Integer.valueOf(endDate);
            cal.add(Calendar.DATE,cal_add);
            regDate = new_format.format(cal.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }

        return regDate;
    }
    public long getLongRegDate() {

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

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public List<String> getMarketPrice() {
        List<String> bs = new ArrayList<String>();
        for(int i=0; i<marketPrice.size(); i++) {
            if (marketPrice.get(i) < 1000000) {
                bs.add(marketPrice.get(i) / 10000 + "만원");
            } else if (1000000 <= marketPrice.get(i) && marketPrice.get(i) < 100000000) {
                bs.add(marketPrice.get(i) / 10000 + "만원");
            } else {
                bs.add(marketPrice.get(i) / 100000000 + "." + (marketPrice.get(i) % 100000000) / 10000000 + "억원");
            }
        }
        return bs;
    }

    public void setMarketPrice(List<Long> marketMoney) {
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
            bs = businessScale/10000 +"만원";
        } else if (1000000 <= businessScale && businessScale < 100000000) {
            bs = businessScale / 10000 + "만원";
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
