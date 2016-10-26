package com.yliyun.index;

import com.yliyun.model.CommonFile;
import com.yliyun.util.AppConfig;
import com.yliyun.util.EsClient;
import com.yliyun.util.SearchDateUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Administrator on 2016/9/30.
 */

@Service
public class IndexDateImpl implements IndexDataService {

    @Autowired
    private AppConfig ac;


    //TransportClient tc = EsClient.getClient();


    private static final Logger logger = LoggerFactory.getLogger(IndexDateImpl.class);

    @Override
    public boolean indexData(CommonFile doc) {

        try {
            IndexResponse ir = getIndexRequestBuilderForAProduct(doc).get();

            // System.out.println( ir.getContext().toString());

            logger.info("IndexDateImpl  --->  indexData ---> result : ", ir.getContext());

           // ac.close();

            // tc.close();

            return true;
        } catch (Exception ex) {
            logger.error("Error occurred while creating index document for product.", ex);
            // throw new RuntimeException(ex);
            ex.printStackTrace();
        }
        return false;

    }

    private IndexRequestBuilder getIndexRequestBuilderForAProduct(CommonFile doc) {

        XContentBuilder contentBuilder = null;

        try {

            contentBuilder = getXContentBuilderForAProduct(doc);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        IndexRequestBuilder indexRequestBuilder = ac.getClient().prepareIndex(ac.getIndexName(), ac.getTypeName(), String.valueOf(doc.getFile_id()));

        indexRequestBuilder.setSource(contentBuilder);

        return indexRequestBuilder;

    }

    private XContentBuilder getXContentBuilderForAProduct(CommonFile doc) throws IOException, ParseException {

        XContentBuilder contentBuilder = null;
        try {
            contentBuilder = jsonBuilder().prettyPrint().startObject();

            contentBuilder.field(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(), doc.getFile_category())
                    .field(SearchDocumentFieldName.FILE_STATUS.getFieldName(), doc.getDel_status())

                    .field(SearchDocumentFieldName.FILE_CREATER_USER_ID.getFieldName(), doc.getCreater_id())
                    .field(SearchDocumentFieldName.FILE_CREATER_USER_NAME.getFieldName(), doc.getCreater_name())
                    .field(SearchDocumentFieldName.FILE_CREATE_TIME.getFieldName(), doc.getCreate_time())

                    .field(SearchDocumentFieldName.FILE_IS_FOLDER.getFieldName(), doc.getFolder())
                    .field(SearchDocumentFieldName.FILE_UPDATE_USER_ID.getFieldName(), doc.getUpdate_user_id())


                    .field(SearchDocumentFieldName.FILE_UPDATE_USER_NAME.getFieldName(), doc.getUpdate_user_name())
                    .field(SearchDocumentFieldName.FILE_UPDATE_TIME.getFieldName(), doc.getUpdate_time())

                    .field(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName(), doc.getDoc_type())
                    .field(SearchDocumentFieldName.FILE_SIZE.getFieldName(), doc.getFile_size())

                    .field(SearchDocumentFieldName.FILE_TITLE.getFieldName(), doc.getFile_name())
                    .field(SearchDocumentFieldName.FILE_CONTENTS.getFieldName(), doc.getFile_contents())


                    .field(SearchDocumentFieldName.FILE_USER_ID.getFieldName(), doc.getUser_id())
                    .field(SearchDocumentFieldName.FILE_ID.getFieldName(), doc.getFile_id())
                    .field(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName(), doc.getGroup_id())
                    .field(SearchDocumentFieldName.FILE_PARENT_ID.getFieldName(), doc.getParent_id())
                    .field(SearchDocumentFieldName.FS_FILE_ID.getFieldName(), doc.getFs_file_id())
            ;

            contentBuilder.endObject();

            return contentBuilder;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public void indexData(Map<String, Object> mapping) {

        // tc.prepareIndex().setSource();
        ac.getClient().prepareIndex(ac.getIndexName(), ac.getTypeName()).setSource(mapping).get();


    }

    @Override
    public void indexBulkData(List<CommonFile> docs) {

        if (docs.isEmpty()) {
            return;
        }


        List<IndexRequestBuilder> requests = new ArrayList<IndexRequestBuilder>();

        for (CommonFile doc : docs) {
            try {
                requests.add(getIndexRequestBuilderForAProduct(doc));
            } catch (Exception ex) {
                logger.error("Error occurred while creating index document for doc with id: " + doc.getFile_id() + ", moving to next doc!", ex);
            }
        }

        processBulkRequests(requests);

    }

    @Override
    public boolean isDocExists(Long fileId) {


        boolean l = ac.getClient().prepareGet().setIndex(ac.getIndexName()).setId(String.valueOf(fileId)).get().isExists();

       // ac.close();

        return l;
    }


    @Override
    public void updateData(Map<String, Object> map, Long fileId) {

        UpdateRequest ur = new UpdateRequest();
        ur.index(ac.getIndexName());
        ur.type(ac.getTypeName());
        ur.id(fileId + "");
        ur.doc(map);
        UpdateResponse urs = ac.getClient().update(ur).actionGet();

      //  ac.close();

    }

    @Override
    public void delData(Long id) {

        DeleteResponse response = ac.getClient().prepareDelete(ac.getIndexName(), ac.getTypeName(), id + "").get();
        System.out.println("del doc data: " + response.getContext().toString());

        logger.debug("del doc data: " + response.getContext().toString());
    }


    protected BulkResponse processBulkRequests(List<IndexRequestBuilder> requests) {
        if (requests.size() > 0) {
            BulkRequestBuilder bulkRequest = ac.getClient().prepareBulk();

            for (IndexRequestBuilder indexRequestBuilder : requests) {
                bulkRequest.add(indexRequestBuilder);
            }

            logger.debug("Executing bulk index request for size:" + requests.size());
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();

            logger.debug("Bulk operation data index response total items is:" + bulkResponse.getItems().length);
            if (bulkResponse.hasFailures()) {
                // process failures by iterating through each bulk response item
                logger.error("bulk operation indexing has failures:" + bulkResponse.buildFailureMessage());
            }
            //ac.close();
            return bulkResponse;
        } else {
            logger.debug("Executing bulk index request for size: 0");
            return null;
        }
    }


}
