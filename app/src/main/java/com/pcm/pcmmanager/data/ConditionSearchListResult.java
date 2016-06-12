package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-25.
 */
public class ConditionSearchListResult {
    int result;
    String message;
    @SerializedName("totalcount")
    int totalCount;

    //확인해봐 리스트 맞는지
    @SerializedName("list")
    List<ConditionSearchList> conditionSearchLists;

    public List<ConditionSearchList> getConditionSearchLists() {
        return conditionSearchLists;
    }

    public void setConditionSearchLists(List<ConditionSearchList> conditionSearchLists) {
        this.conditionSearchLists = conditionSearchLists;
    }
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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
