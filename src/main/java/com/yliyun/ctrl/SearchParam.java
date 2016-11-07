package com.yliyun.ctrl;/**
 * Created by Administrator on 2016/11/7.
 */

import java.io.Serializable;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/11/7 - 12:04
 *
 */
public class SearchParam  implements Serializable {

    private Long userId;
    private String keyword;
    private int from;
    private int size;

    public SearchParam() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "SearchParam{" +
                "userId=" + userId +
                ", keyword='" + keyword + '\'' +
                ", from=" + from +
                ", size=" + size +
                '}';
    }
}
