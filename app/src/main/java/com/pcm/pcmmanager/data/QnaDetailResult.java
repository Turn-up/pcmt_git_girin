package com.pcm.pcmmanager.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailResult {
    int result;
    String message;
    @SerializedName("item")
    QnaDetail qnaDetail;

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

    public QnaDetail getQnaDetail() {
        return qnaDetail;
    }

    public void setQnaDetail(QnaDetail qnaDetail) {
        this.qnaDetail = qnaDetail;
    }
}
