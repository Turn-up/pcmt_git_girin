package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-05-24.
 */
public class ExpertEstimateResult {
    int result;
    String message;
    @SerializedName("totalcount")
    int totalCount;
    @SerializedName("list")
    List<ExpertEstimateList> estimateList;

    public List<ExpertEstimateList> getEstimateList() {
        return estimateList;
    }

    public void setEstimateList(List<ExpertEstimateList> estimateList) {
        this.estimateList = estimateList;
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
