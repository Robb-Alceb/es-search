package com.yliyun.index;

import com.yliyun.model.DocumentData;
import com.yliyun.model.EsIndexConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface IndexData {

    // 根据产品对象进行索引
    void indexData(EsIndexConfig config, DocumentData doc);

    // 自定义map对象进行索引
    void indexData(EsIndexConfig config, Map<String, Object> map);


    // 批量索引
    void indexBulkData(EsIndexConfig config, List<DocumentData> docs);

    // 该文件是否已经被索引
    boolean isDocExists(EsIndexConfig config, String fileId);


    void updateData(EsIndexConfig config, Map<String, Object> map);


    // 删除该条索引
    void delData(EsIndexConfig config, String id);


}
