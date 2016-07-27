package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-07-27.
 */
public class FaqList {
    int faqsn;
    String title;
    String content;
    String regdate;

    public int getFaqsn() {
        return faqsn;
    }

    public void setFaqsn(int faqsn) {
        this.faqsn = faqsn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
