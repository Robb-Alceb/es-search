package com.yliyun.model;

/**
 * Created by Administrator on 2016/10/1.
 */
public class DocumentData {

    private String Id;

    private String fileId;
    private String fileName;

    // 文件的内容
    private String contents;
    // 文件类型
    private String extName;
    // 文件上传时间
    private String createTime;

    //文件的删除状态
    private String fileStatus;

    // 文件逻辑分区
    private String category;

    // 上传用户名称
    private String userName;


    private String userId;

    // 姓名
    private String realName;

    private String groupId;

    // 群组名称
    private String groupName;

    // 标签
    private String tagName;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getExtName() {
        return this.fileName.substring(this.fileName.lastIndexOf("."));
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
