package com.yliyun.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/1.
 */
public class DocumentData {


    private String fileId;

    private String fileTitle;

    private int fileSize;

    // 文件的内容
    private String fileContents;
    // 文件类型
    private String fileExtName;

    // 文件上传时间
    private Date createTime;

    // 文件上传时间
    private Date updateTime;

    //文件的删除状态
    private String fileStatus;

    // 文件逻辑分区
    private String fileCategory;

    private boolean fileIsFolder;

    // 上传用户名称
    private String userName;


    private String userId;

    // 姓名
    private String realName;

    private String groupId;

    // 标签
    private String tagName;

    public DocumentData() {

    }

    public DocumentData(String fileId, String fileTitle, int fileSize, String fileContents, String fileExtName, Date createTime, Date updateTime, String fileStatus, String fileCategory, boolean fileIsFolder, String userName, String userId, String realName, String groupId, String tagName) {
        this.fileId = fileId;
        this.fileTitle = fileTitle;
        this.fileSize = fileSize;
        this.fileContents = fileContents;
        this.fileExtName = fileExtName;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.fileStatus = fileStatus;
        this.fileCategory = fileCategory;
        this.fileIsFolder = fileIsFolder;
        this.userName = userName;
        this.userId = userId;
        this.realName = realName;
        this.groupId = groupId;
        this.tagName = tagName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public String getFileExtName() {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public boolean isFileIsFolder() {
        return fileIsFolder;
    }

    public void setFileIsFolder(boolean fileIsFolder) {
        this.fileIsFolder = fileIsFolder;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "DocumentData{" +
                "fileId='" + fileId + '\'' +
                ", fileTitle='" + fileTitle + '\'' +
                ", fileSize=" + fileSize +
                ", fileContents='" + fileContents + '\'' +
                ", fileExtName='" + fileExtName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", fileStatus='" + fileStatus + '\'' +
                ", fileCategory='" + fileCategory + '\'' +
                ", fileIsFolder=" + fileIsFolder +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", realName='" + realName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
