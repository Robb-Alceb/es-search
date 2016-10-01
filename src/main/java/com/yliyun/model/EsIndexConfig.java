package com.yliyun.model;

/**
 * Created by Administrator on 2016/9/23.
 */
public class EsIndexConfig {

    private String indexName;
    private String typeName;

    public EsIndexConfig(String indexName, String typeName) {
        this.indexName = indexName.toLowerCase();
        this.typeName = typeName.toLowerCase();
    }

    public String getIndexName() {
        return indexName.toLowerCase();
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName.toLowerCase();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
