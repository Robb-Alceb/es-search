package com.yliyun.index;

import com.yliyun.client.EsClient;
import com.yliyun.model.EsIndexConfig;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.cluster.snapshots.status.TransportNodesSnapshotsStatus;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsAction;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.exists.ExistsAction;
import org.elasticsearch.action.support.PlainActionFuture;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/29.
 */
public class SetupIndexServiceImpl implements SetupIndexService {

    TransportClient tc = EsClient.getInstance();


    @Override
    public boolean isIndexExists(String indexName) {
        //System.out.printf();

        ListenableActionFuture<IndicesExistsResponse> ll = tc.admin().indices().prepareExists(indexName).execute();

        try {
            IndicesExistsResponse ie = ll.get();
            System.out.printf(ie.getContext().toString());
            System.out.println(ie.isExists());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tc.close();
        return true;
    }

    @Override
    public boolean deleteIndex(String indexName) {

        tc.admin().indices().prepareDelete(indexName).execute();
        tc.close();
        return true;
    }

    @Override
    public boolean createIndex(EsIndexConfig config) {

        Settings settings = Settings.settingsBuilder()
                .put("index.number_of_shards", "3")
                .put("index.number_of_replicas", "3").build();

        CreateIndexRequest cir = new CreateIndexRequest(config.getIndexName(), settings);

        try {

            CreateIndexResponse ci = tc.admin().indices().prepareCreate(config.getIndexName()).execute().actionGet();

            tc.close();

            return true;
            //  System.out.println(ci.getContext());

        } catch (Exception ex) {

            System.out.println("has ex : " + ex);

            return false;

        }
    }

    @Override
    public boolean createMapping(EsIndexConfig config, XContentBuilder mappingBuilder) throws IOException, ExecutionException, InterruptedException {

//        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
//                .startObject()
//                .startObject("properties")
//                .startObject("id").field("type", "string").field("index", "not_analyzed").endObject()
//                .startObject("content").field("type", "string").field("analyzer", "ik_max_word").endObject()
//                .endObject()
//                .endObject();

        ListenableActionFuture<PutMappingResponse>  pb =  tc.admin().indices().preparePutMapping(config.getIndexName()).setType(config.getTypeName()).setSource(mappingBuilder).execute();

        System.out.println(pb.get().getContext().toString());

        tc.close();
        return false;
    }

    @Override
    public boolean updateMapping(EsIndexConfig config, XContentBuilder mapping) throws IOException {

//        XContentBuilder mappingBuilder  =XContentFactory.jsonBuilder().startObject()
//                .startObject("properties")
//                .startObject("id").field("type","string").field("index","not_analyzed").endObject()
//                .startObject("content").field("type","string").field("analyzer","ik_max_word").endObject();

        ListenableActionFuture<PutMappingResponse> mappRes = tc.admin().indices().preparePutMapping(config.getIndexName()).setType(config.getTypeName()).setSource(mapping).execute();
        try {
            PutMappingResponse pmr = mappRes.get();
            pmr.getContext();
            System.out.println(pmr.getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }
}
