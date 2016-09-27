package com.yliyun.index;

import com.yliyun.model.EsIndexConfig;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface SetupIndexService {


    boolean isIndexExists(String indexName);

    boolean deleteIndex(String indexName);

    void createIndex(EsIndexConfig config);


}
