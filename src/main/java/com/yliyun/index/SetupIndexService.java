package com.yliyun.index;

import com.yliyun.model.EsIndexConfig;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface SetupIndexService {


    boolean isIndexExists(String indexName);

    boolean deleteIndex(String indexName);

    boolean createIndex(EsIndexConfig config);

    boolean createMapping(EsIndexConfig config,XContentBuilder xContentBuilder) throws IOException, ExecutionException, InterruptedException;

    boolean updateMapping(EsIndexConfig config, XContentBuilder mapping) throws IOException;


}
