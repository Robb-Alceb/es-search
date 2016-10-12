package com.yliyun.index;

/**
 * Created by manbuzhiwu on 2016/10/1.
 */
public enum SearchDocumentFieldName
{
    FILE_ID("file_id"),
    FILE_USER_ID("file_user_id"),
    FILE_GROUP_ID("file_group_id"),



    FILE_TITLE("file_title"),// 标题
    FILE_CONTENTS("file_contents"),// 内容


    FILE_STATUS("file_status"), // 状态
    FILE_IS_FOLDER("file_is_folder"),



    FILE_SIZE("file_size"),


    FILE_EXT_NAME("file_ext_name"),// 格式
    FILE_CATEGORY("file_category"),// 分区


    FILE_USER_NAME("file_user_name"), // 关联用户
    FILE_REAL_NAME("file_real_name"),// 用户姓名





    FILE_CREATE_TIME("file_create_time"),
    FILE_UPDATE_TIME("file_update_time"),






    BOOSTFACTOR("boostfactor"),

    DESCRIPTION("description"),

    AVAILABLE_DATE("availabledate"),

    SOLD_OUT("soldout"),

    KEYWORDS("keywords"),

    CATEGORIES_ARRAY("categories"),

    PRICE("price"),

    FACET("facet"),

    FACETFILTER("facetfilter"),

    SUGGEST("suggest"),
    SEQUENCED("sequenced"),

    SIZE("size"),
    COLOR("color"),

    SPECIFICATIONS("specifications"),
    RESOLUTION("resolution"),
    MEMORY("memory")
    ;

    public static final String[] productQueryFields = {
            FILE_TITLE.getFieldName(),
            PRICE.getFieldName(),
            SOLD_OUT.getFieldName()
    };

    public static final String[] productDocumentFields = {

            FILE_ID.getFieldName(),
            FILE_USER_ID.getFieldName(),
            FILE_GROUP_ID.getFieldName(),

            FILE_TITLE.getFieldName(),
            FILE_CONTENTS.getFieldName(),
            FILE_SIZE.getFieldName(),
            FILE_CATEGORY.getFieldName(),
            FILE_EXT_NAME.getFieldName(),

            FILE_CREATE_TIME.getFieldName(),
            FILE_UPDATE_TIME.getFieldName(),

            FILE_STATUS.getFieldName(),
            FILE_IS_FOLDER.getFieldName(),

            FILE_USER_NAME.getFieldName(),
            FILE_REAL_NAME.getFieldName(),

    };

    public static final String[] allStoreTextDocumentFields = {

            FILE_ID.getFieldName(),

            FILE_USER_ID.getFieldName(),
            FILE_GROUP_ID.getFieldName(),

            FILE_SIZE.getFieldName(),

            FILE_CATEGORY.getFieldName(),
            FILE_EXT_NAME.getFieldName(),

            FILE_USER_NAME.getFieldName(),
            FILE_REAL_NAME.getFieldName(),
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

