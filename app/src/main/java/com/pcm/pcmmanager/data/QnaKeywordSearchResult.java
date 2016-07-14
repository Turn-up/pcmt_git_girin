package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-07-05.
 */
public class QnaKeywordSearchResult {
    int result;
    String message;
    int totalcount;
    @SerializedName("list")
    List<QnaList> keywordSearchList;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public List<QnaList> getKeywordSearchList() {
        return keywordSearchList;
    }

    public void setKeywordSearchList(List<QnaList> keywordSearchList) {
        this.keywordSearchList = keywordSearchList;
    }
}
