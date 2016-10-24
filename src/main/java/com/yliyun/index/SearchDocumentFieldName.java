package com.yliyun.index;

/**
 * Created by manbuzhiwu on 2016/10/1.
 */
public enum SearchDocumentFieldName
{

    // all id
    FILE_ID("file_id"),
    FILE_USER_ID("file_user_id"),
    FILE_GROUP_ID("file_group_id"),
    FILE_DEPT_ID("file_dept_id"),

    FILE_PARENT_ID("file_parent_id"),
    FS_FILE_ID("fs_file_id"),


    // 全文分析
    FILE_TITLE("file_title"),// 标题
    FILE_CONTENTS("file_contents"),// 内容


    // filter boolean 状态
    FILE_STATUS("file_status"), // 状态
    FILE_IS_FOLDER("file_is_folder"),

    // filter other

    FILE_EXT_NAME("file_ext_name"),// 格式
    FILE_CATEGORY("file_category"),// 分区
    FILE_CREATER_USER_NAME("file_create_user_name"),
    FILE_UPDATE_USER_NAME("file_update_user_name"),

    // filter date
    FILE_CREATE_TIME("file_create_time"),
    FILE_UPDATE_TIME("file_update_time"),


    // filter date
    FILE_SIZE("file_size"),
    FILE_CREATER_USER_ID("file_create_user_id"),
    FILE_UPDATE_USER_ID("file_update_user_id"),

    ;


    // all data;
    public static final String[] productDocumentFields = {

            FILE_ID.getFieldName(),
            FILE_USER_ID.getFieldName(),
            FILE_GROUP_ID.getFieldName(),
            FILE_DEPT_ID.getFieldName(),
            FILE_PARENT_ID.getFieldName(),
            FS_FILE_ID.getFieldName(),

            FILE_TITLE.getFieldName(),
            FILE_CONTENTS.getFieldName(),
            FILE_SIZE.getFieldName(),
            FILE_CATEGORY.getFieldName(),
            FILE_EXT_NAME.getFieldName(),

            FILE_CREATE_TIME.getFieldName(),
            FILE_UPDATE_TIME.getFieldName(),

            FILE_STATUS.getFieldName(),
            FILE_IS_FOLDER.getFieldName(),

            FILE_UPDATE_USER_NAME.getFieldName(),
            FILE_CREATER_USER_NAME.getFieldName(),

            FILE_CREATER_USER_ID.getFieldName(),
            FILE_UPDATE_USER_ID.getFieldName(),


    };

    public static final String[] allStoreTextDocumentFields = {

            FILE_ID.getFieldName(),

            FILE_USER_ID.getFieldName(),
            FILE_GROUP_ID.getFieldName(),
            FILE_DEPT_ID.getFieldName(),

            FILE_PARENT_ID.getFieldName(),
            FS_FILE_ID.getFieldName(),

            FILE_SIZE.getFieldName(),

            FILE_CATEGORY.getFieldName(),
            FILE_EXT_NAME.getFieldName(),

            FILE_UPDATE_USER_NAME.getFieldName(),
            FILE_CREATER_USER_NAME.getFieldName(),

            FILE_CREATER_USER_ID.getFieldName(),
            FILE_UPDATE_USER_ID.getFieldName(),
    };

    public static final String[] dateTextDocumentFields = {

            FILE_CREATE_TIME.getFieldName(),
            FILE_UPDATE_TIME.getFieldName(),
    };

    // bool类型
    public static final String[] boolTextDocumentFields = {

            FILE_IS_FOLDER.getFieldName(),
            FILE_STATUS.getFieldName(),
    };


    public static final String[] fullTextDocumentFields = {

            FILE_TITLE.getFieldName(),
            FILE_CONTENTS.getFieldName(),
    };


    private String fieldName;

    private SearchDocumentFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public static String getCalculatedScoreScriptForBostFactor()
    {
        return "_score + doc.boostfactor.value";
    }

}

