package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by LG on 2016-06-30.
 */
public class QnaMyListResult {
    int result;
    String message;
    int totlacount;

    @SerializedName("list")
    List<QnaMyList> qnaList;

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

    public List<QnaMyList> getQnaList() {
        return qnaList;
    }

    public void setQnaList(List<QnaMyList> qnaList) {
        this.qnaList = qnaList;
    }

    public int getTotlacount() {
        return totlacount;
    }

    public void setTotlacount(int totlacount) {
        this.totlacount = totlacount;
    }
}
