package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QnaListResult {
    int result;
    String message;
    int totalcount;

    @SerializedName("list")
    List<QnaList> qnaList;

    public List<QnaList> getQnaList() {
        return qnaList;
    }

    public void setQnaList(List<QnaList> qnaList) {
        this.qnaList = qnaList;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
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
