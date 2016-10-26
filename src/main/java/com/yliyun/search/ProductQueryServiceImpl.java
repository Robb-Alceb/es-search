package com.yliyun.search;
/**
 * Created by Administrator on 2016/10/11.
 */

import com.yliyun.index.SearchDocumentFieldName;
import com.yliyun.model.DocumentData;
import com.yliyun.util.AppConfig;
import com.yliyun.util.EsClient;
import com.yliyun.util.SearchDateUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 15:29
 *
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    
    @Autowired
    private AppConfig ac;

    //TransportClient tc = EsClient.getInstance();


    @Override
    public ProductSearchResult searchProducts( SearchCriteria searchCriteria) {

        SearchRequestBuilder searchBuild = getSearchRequestBuilder(searchCriteria);

        searchBuild.addFields(SearchDocumentFieldName.productDocumentFields);
        searchBuild.setSearchType(SearchType.DEFAULT);

        //  QueryBuilder qb = existsQuery("name");

        // searchBuild.setPostFilter();

        // searchBuild.setAggregations();

        return null;
    }

    @Override
    public ProductSearchResult baseSearch( String keyword) {

        QueryBuilder tqb = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_CREATER_USER_NAME.getFieldName(), "manbu2"))
                .must(termQuery(SearchDocumentFieldName.FILE_UPDATE_USER_NAME.getFieldName(), "漫步"));
        QueryBuilder qb = multiMatchQuery(
                keyword,
                SearchDocumentFieldName.FILE_TITLE.getFieldName(), SearchDocumentFieldName.FILE_CONTENTS.getFieldName()
        );

        SearchResponse sr = ac.getClient().prepareSearch(ac.getIndexName()).setTypes(ac.getTypeName())
                .setQuery(qb).setPostFilter( tqb)
                .execute().actionGet();

        System.out.println(sr.getHits().totalHits());

        for (SearchHit searchHit : sr.getHits()) {
             DocumentData doc = new  DocumentData();

            System.out.println(searchHit.getId());
            System.out.println(searchHit.getSourceAsString());

//            product.setId(Long.valueOf(searchHit.getId()));
//            doc.setFileTitle(getFieldValueOrNull(searchHit, SearchDocumentFieldName.FILE_TITLE.getFieldName());
//            product.setPrice(BigDecimal.valueOf(getDoubleFieldValueOrNull(searchHit, SearchDocumentFieldName.PRICE.getFieldName())));
//            product.setSoldOut(Boolean.valueOf(getFieldValueOrNull(searchHit, SearchDocumentFieldName.SOLD_OUT.getFieldName())));
//
//            productSearchResult.addProduct(product);
        }

        return null;
    }


    protected String getStringFieldValue(SearchHit field)
    {
        if(field !=null)
        {
//            return String.valueOf(field.get());
        }
        return null;
    }

    private SearchRequestBuilder AddAggregations(SearchRequestBuilder searchBuild) {

        // AggregationBuilders.terms("").field();

        return searchBuild;
    }

    private SearchRequestBuilder getSearchRequestBuilder(SearchCriteria searchCriteria) {


        return null;

    }

    @Override
    public DocumentData getProduct( String productId) {

        //  System.out.println(productId);

        GetResponse getResponse = ac.getClient().prepareGet(ac.getIndexName(), ac.getTypeName(), productId)
                .setFields(SearchDocumentFieldName.productDocumentFields).execute().actionGet();
        //.setFields(SearchDocumentFieldName.allStoreTextDocumentFields) 对请求设置对象，否则无法自动赋值


        System.out.println(getResponse.getId());
        System.out.println(getResponse.isExists());
        System.out.println(getResponse.getSourceAsString());


      //  ac.close();

        if (getResponse.isExists()) {

            DocumentData dd = new DocumentData();
            dd.setFileId(getResponse.getId());
            System.out.println(getResponse.getField(SearchDocumentFieldName.FILE_CATEGORY.getFieldName()).getValue());
            dd.setFileCategory(getResponse.getField(SearchDocumentFieldName.FILE_CATEGORY.getFieldName()).getValue().toString());
            dd.setFileContents(getResponse.getField(SearchDocumentFieldName.FILE_CONTENTS.getFieldName()).getValue().toString());
            dd.setFileExtName(getResponse.getField(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName()).getValue().toString());
            // true or false
            dd.setFileIsFolder(Boolean.valueOf(getResponse.getField(SearchDocumentFieldName.FILE_IS_FOLDER.getFieldName()).getValue().toString()));
            dd.setFileStatus(getResponse.getField(SearchDocumentFieldName.FILE_STATUS.getFieldName()).getValue().toString());
            dd.setFileTitle(getResponse.getField(SearchDocumentFieldName.FILE_TITLE.getFieldName()).getValue().toString());
            dd.setGroupId(getResponse.getField(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName()).getValue().toString());
            dd.setRealName(getResponse.getField(SearchDocumentFieldName.FILE_UPDATE_USER_NAME.getFieldName()).getValue().toString());
            //dd.setTagName();
            dd.setUserName(getResponse.getField(SearchDocumentFieldName.FILE_CREATER_USER_NAME.getFieldName()).getValue().toString());
            dd.setUserId(getResponse.getField(SearchDocumentFieldName.FILE_USER_ID.getFieldName()).getValue().toString());
            // integer
            dd.setFileSize(Integer.valueOf(getResponse.getField(SearchDocumentFieldName.FILE_SIZE.getFieldName()).getValue().toString()));

            // date time
            dd.setCreateTime(SearchDateUtils.getFormattedDate(getResponse.getField(SearchDocumentFieldName.FILE_CREATE_TIME.getFieldName()).getValue().toString()));
            dd.setUpdateTime(SearchDateUtils.getFormattedDate(getResponse.getField(SearchDocumentFieldName.FILE_UPDATE_TIME.getFieldName()).getValue().toString()));

            return dd;
        }

        return null;
    }

    @Override
    public List<AutoSuggestionEntry> getAutoSuggestions( String queryString) {
        return null;
    }

    @Override
    public List<AutoSuggestionEntry> getAutoSuggestionsUsingTermsFacet( String string) {
        return null;
    }

    @Override
    public List<DocumentData> findSimilarProducts( String[] fields, String productId) {
        return null;
    }
}
