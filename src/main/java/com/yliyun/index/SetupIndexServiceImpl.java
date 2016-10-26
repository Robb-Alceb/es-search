package com.yliyun.index;

import com.yliyun.util.AppConfig;
import com.yliyun.util.EsClient;
import com.yliyun.util.TikaUtils;
import org.apache.tika.Tika;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/29.
 */
@Service
public class SetupIndexServiceImpl implements SetupIndexService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupIndexServiceImpl.class);



    @Autowired
    private AppConfig ac;


    @Override
    public boolean isIndexExists(String indexName) {

        ListenableActionFuture<IndicesExistsResponse> ll = ac.getClient().admin().indices().prepareExists(indexName).execute();

        try {
            IndicesExistsResponse ie = ll.get();

            LOGGER.info("SetupIndexServiceImpl --->  isIndexExists -----> result : ", ie.getContext().toString());

            ac.close();

            return ie.isExists();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteIndex(String indexName) {

        DeleteIndexResponse del = null;
        try {
            del = ac.getClient().admin().indices().prepareDelete(indexName).execute().get();
            LOGGER.info("SetupIndexServiceImpl --->  deleteIndex -----> result : ", del.getContext().toString());
            ac.close();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean createIndex() {
        // 注意此处设置
        Settings settings = Settings.settingsBuilder()
                .put("index.number_of_shards", "1")
                .put("index.number_of_replicas", "1").build();

        CreateIndexRequest cir = new CreateIndexRequest(ac.getIndexName(), settings);

        try {
            CreateIndexResponse cis = ac.getClient().admin().indices().create(cir).actionGet();
            LOGGER.info("SetupIndexServiceImpl --->  createIndex -----> result : ", cis.getContext().toString());
            ac.close();

            return true;

        } catch (Exception ex) {
            LOGGER.error("SetupIndexServiceImpl --->  createIndex -----> ex---- : ", ex);
            return false;

        }
    }

    @Override
    public boolean createMapping(XContentBuilder mappingBuilder) {

        PutMappingResponse pb = null;

        System.out.println("---------------"+ mappingBuilder);

        try {
            pb = ac.getClient().admin().indices()
                    .preparePutMapping(ac.getIndexName()).setType(ac.getTypeName()).setSource(mappingBuilder).execute().get();

            LOGGER.info("SetupIndexServiceImpl --->  createIndex -----> result : ", pb.getContext().toString());

            ac.close();
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMapping(XContentBuilder mapping) throws IOException {


        PutMappingResponse mappRes = null;
        try {
            mappRes = ac.getClient().admin().indices().preparePutMapping(ac.getIndexName()).setType(ac.getTypeName()).setSource(mapping).execute().get();
            LOGGER.info("SetupIndexServiceImpl --->  updateMapping -----> result : ", mappRes.getContext().toString());
            ac.close();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
