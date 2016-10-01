package com.yliyun.index;

import com.yliyun.model.EsIndexConfig;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/1.
 */
public class IndexMappingBuild {


    public XContentBuilder getDocumentTypeMapping(EsIndexConfig config){

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
