package com.yliyun.index;

import com.yliyun.client.EsClient;
import com.yliyun.model.DocumentData;
import com.yliyun.model.EsIndexConfig;
import com.yliyun.util.SearchDateUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Administrator on 2016/9/30.
 */
public class IndexDateImpl implements IndexData {

    TransportClient tc = EsClient.getInstance();


    private static final Logger logger = LoggerFactory.getLogger(IndexDateImpl.class);

    @Override
    public void indexData(EsIndexConfig config, DocumentData doc) {

        try {
            getIndexRequestBuilderForAProduct(doc, config).get();
            tc.close();
        } catch (Exception ex) {
            logger.error("Error occurred while creating index document for product.", ex);
            throw new RuntimeException(ex);
        }

    }

    private IndexRequestBuilder getIndexRequestBuilderForAProduct(DocumentData doc, EsIndexConfig config) {

        XContentBuilder contentBuilder = null;
        try {
            contentBuilder = getXContentBuilderForAProduct(doc);

        } catch (IOException e) {
            e.printStackTrace();
        }

        IndexRequestBuilder indexRequestBuilder = tc.prepareIndex(config.getIndexName(), config.getTypeName(),String.valueOf(doc.getFileId()));

        indexRequestBuilder.setSource(contentBuilder);

        return indexRequestBuilder;

    }

    private XContentBuilder getXContentBuilderForAProduct(DocumentData doc) throws IOException {

        XContentBuilder contentBuilder = null;
        try {
            contentBuilder = jsonBuilder().prettyPrint().startObject();

            contentBuilder.field(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(), doc.getFileCategory())
                    .field(SearchDocumentFieldName.FILE_STATUS.getFieldName(), doc.getFileStatus())
                    .field(SearchDocumentFieldName.FILE_USER_NAME.getFieldName(), doc.getUserName())
                    .field(SearchDocumentFieldName.FILE_IS_FOLDER.getFieldName(), doc.isFileIsFolder())
                    .field(SearchDocumentFieldName.FILE_CREATE_TIME.getFieldName(), SearchDateUtils.formatDate(doc.getCreateTime()))
                    .field(SearchDocumentFieldName.FILE_UPDATE_TIME.getFieldName(), SearchDateUtils.formatDate(doc.getUpdateTime()))
                    .field(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName(), doc.getGroupId())
                    .field(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName(), doc.getGroupId())
                    .field(SearchDocumentFieldName.FILE_ID.getFieldName(), doc.getGroupId())
                    .field(SearchDocumentFieldName.FILE_REAL_NAME.getFieldName(), doc.getRealName())
                    .field(SearchDocumentFieldName.FILE_SIZE.getFieldName(), doc.getFileSize())
                    .field(SearchDocumentFieldName.FILE_CONTENTS.getFieldName(), doc.getFileContents())
                    .field(SearchDocumentFieldName.FILE_TITLE.getFieldName(), doc.getFileTitle())
                    .field(SearchDocumentFieldName.FILE_USER_ID.getFieldName(), doc.getUserId())
            ;

            contentBuilder.endObject();

            return contentBuilder;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public void indexData(EsIndexConfig config, Map<String, Object> mapping) {

        // tc.prepareIndex().setSource();
        tc.prepareIndex(config.getIndexName(), config.getTypeName()).setSource(mapping).get();


    }

    @Override
    public void indexBulkData(EsIndexConfig config, List<DocumentData> docs) {
        if(docs.isEmpty()){
            return ;
        }
        List<IndexRequestBuilder> requests = new ArrayList<IndexRequestBuilder>();

        for (DocumentData doc : docs)
        {
            try
            {
                requests.add(getIndexRequestBuilderForAProduct(doc, config));
            } catch (Exception ex)
            {
                logger.error("Error occurred while creating index document for doc with id: " + doc.getFileId() + ", moving to next doc!", ex);
            }
        }

        processBulkRequests(requests);

    }

    @Override
    public boolean isDocExists(EsIndexConfig config, String fileId) {

        return tc.prepareGet().setIndex(config.getIndexName()).setId(String.valueOf(fileId)).get().isExists();
    }


    @Override
    public void updateData(EsIndexConfig config, Map<String, Object> map) {

        UpdateRequest ur = new UpdateRequest();
        ur.index(config.getIndexName());
        ur.type(config.getTypeName());
        ur.id(map.get("_id").toString());
        ur.doc(map);
        tc.update(ur).actionGet();
    }

    @Override
    public void delData(EsIndexConfig config, String id) {

        DeleteResponse response = tc.prepareDelete(config.getIndexName(), config.getTypeName(), id).get();

        System.out.println("del doc data: "+response.getContext().toString());

        logger.debug("del doc data: "+response.getContext().toString());
    }


    protected BulkResponse processBulkRequests(List<IndexRequestBuilder> requests)
    {
        if (requests.size() > 0)
        {
            BulkRequestBuilder bulkRequest = tc.prepareBulk();

            for (IndexRequestBuilder indexRequestBuilder : requests)
            {
                bulkRequest.add(indexRequestBuilder);
            }

            logger.debug("Executing bulk index request for size:" + requests.size());
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();

            logger.debug("Bulk operation data index response total items is:" + bulkResponse.getItems().length);
            if (bulkResponse.hasFailures())
            {
                // process failures by iterating through each bulk response item
                logger.error("bulk operation indexing has failures:" + bulkResponse.buildFailureMessage());
            }
            tc.close();
            return bulkResponse;
        }
        else
        {
            logger.debug("Executing bulk index request for size: 0");
            return null;
        }
    }


}
