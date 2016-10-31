package com.yliyun.search;/**
 * Created by Administrator on 2016/10/31.
 */

import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/31 - 14:13
 *
 */
public class SearchResult {

    private String agg;

    private List<String> fileList;


    public String getAgg() {
        return agg;
    }

    public void setAgg(String agg) {
        this.agg = agg;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
