package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-06-29.
 */
public class QnaReviewResult {
    int result;
    String message;
    QnaReview qnaReview;

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

    public QnaReview getQnaReview() {
        return qnaReview;
    }

    public void setQnaReview(QnaReview qnaReview) {
        this.qnaReview = qnaReview;
    }
}
