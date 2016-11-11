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
    private int offset;
    private int limit;
    private String fileCategory;
    private String groupId;
    private String docTypoe;


    public SearchParam() {
    }

    public String getDocTypoe() {
        return docTypoe;
    }

    public void setDocTypoe(String docTypoe) {
        this.docTypoe = docTypoe;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "SearchParam{" +
                "userId=" + userId +
                ", keyword='" + keyword + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", fileCategory='" + fileCategory + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
