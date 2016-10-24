package com.yliyun.index;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface SetupIndexService {


    boolean isIndexExists(String indexName);

    boolean deleteIndex(String indexName);

    boolean createIndex();

    boolean createMapping(XContentBuilder xContentBuilder) throws IOException, ExecutionException, InterruptedException;

    boolean updateMapping(XContentBuilder mapping) throws IOException;


}
