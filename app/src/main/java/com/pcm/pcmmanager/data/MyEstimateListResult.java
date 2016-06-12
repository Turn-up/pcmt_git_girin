package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-18.
 */
public class MyEstimateListResult {
    int result; //결과
    String message; // 메세지
    @SerializedName("totalcount")
    int totalCount; // 전체 매물수
    List<MyEsitmateList> list;

    public void setResult(int result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<MyEsitmateList> list) {
        this.list = list;
    }

    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<MyEsitmateList> getList() {
        return list;
    }
}
