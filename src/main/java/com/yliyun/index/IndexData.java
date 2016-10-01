package com.yliyun.index;

import com.yliyun.model.EsIndexConfig;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface IndexData {

    void indexData(EsIndexConfig config, Map<String,Object> map);

    void updateData(EsIndexConfig config, Map<String,Object> map);

    void delData(EsIndexConfig config, String id);


}
