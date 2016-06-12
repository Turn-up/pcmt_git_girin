package com.pcm.pcmmanager.data;

import java.util.List;

/**
 * Created by LG on 2016-05-28.
 */
public class ExpertBidStatusResult {
    int result;
    String message;
    int totalcount;
    List<ExpertBidStatus> list;

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

    public List<ExpertBidStatus> getList() {
        return list;
    }

    public void setList(List<ExpertBidStatus> list) {
        this.list = list;
    }
}
