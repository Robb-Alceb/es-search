package com.yliyun.index;

import com.yliyun.model.CommonFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface IndexDataService {

    // 根据产品对象进行索引
    boolean indexData(CommonFile doc);

    // 自定义map对象进行索引
    void indexData(Map<String, Object> map);


    // 批量索引
    void indexBulkData(List<CommonFile> docs);

    // 该文件是否已经被索引
    boolean isDocExists(Long fileId);


    void updateData(Map<String, Object> map);


    // 删除该条索引
    void delData(Long id);


}
