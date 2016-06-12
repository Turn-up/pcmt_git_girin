package com.pcm.pcmmanager.data;

import java.util.List;

/**
 * Created by LG on 2016-05-31.
 */
public class CommonCodeListResult {
    int result;
    String message;
    List<CommonCodeList> codelist;
    List<CommonRegionList> regionlist;


    public List<CommonRegionList> getRegionlist() {
        return regionlist;
    }

    public void setRegionlist(List<CommonRegionList> regionlist) {
        this.regionlist = regionlist;
    }


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

    public List<CommonCodeList> getCodelist() {
        return codelist;
    }

    public void setCodelist(List<CommonCodeList> codelist) {
        this.codelist = codelist;
    }
}
