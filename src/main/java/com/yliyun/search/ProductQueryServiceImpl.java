package com.yliyun.search;/**
 * Created by Administrator on 2016/10/11.
 */

import com.yliyun.client.EsClient;
import com.yliyun.index.SearchDocumentFieldName;
import com.yliyun.model.DocumentData;
import com.yliyun.model.EsIndexConfig;
import com.yliyun.util.SearchDateUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 15:29
 *
 */
public class ProductQueryServiceImpl implements ProductQueryService {

    TransportClient tc = EsClient.getInstance();


    @Override
    public ProductSearchResult searchProducts(EsIndexConfig config, SearchCriteria searchCriteria) {

        SearchRequestBuilder searchBuild = getSearchRequestBuilder(searchCriteria);

        searchBuild.addFields(SearchDocumentFieldName.productDocumentFields);
        searchBuild.setSearchType(SearchType.DEFAULT);

        //  QueryBuilder qb = existsQuery("name");

        // searchBuild.setPostFilter();

        // searchBuild.setAggregations();

        return null;
    }

    @Override
    public ProductSearchResult baseSearch(EsIndexConfig config, String keyword) {

        QueryBuilder tqb = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_REAL_NAME.getFieldName(), "manbu1"));
        QueryBuilder qb = multiMatchQuery(
                keyword,
                SearchDocumentFieldName.FILE_TITLE.getFieldName(), SearchDocumentFieldName.FILE_CONTENTS.getFieldName()
        );

        SearchResponse sr = tc.prepareSearch(config.getIndexName()).setTypes(config.getTypeName())
                .setQuery(tqb)
                .setQuery(qb)
                .execute().actionGet();

        System.out.println(sr.getHits().totalHits());

        for (SearchHit searchHit : sr.getHits()) {
            // Product product = new  Product();

            System.out.println(searchHit.getId());
            System.out.println(searchHit.getSourceAsString());

//            product.setId(Long.valueOf(searchHit.getId()));
//            product.setTitle(getFieldValueOrNull(searchHit, SearchDocumentFieldName.TITLE.getFieldName()));
//            product.setPrice(BigDecimal.valueOf(getDoubleFieldValueOrNull(searchHit, SearchDocumentFieldName.PRICE.getFieldName())));
//            product.setSoldOut(Boolean.valueOf(getFieldValueOrNull(searchHit, SearchDocumentFieldName.SOLD_OUT.getFieldName())));
//
//            productSearchResult.addProduct(product);
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
    public DocumentData getProduct(EsIndexConfig config, String productId) {

        //  System.out.println(productId);

        GetResponse getResponse = tc.prepareGet(config.getIndexName(), config.getTypeName(), productId)
                .setFields(SearchDocumentFieldName.productDocumentFields).execute().actionGet();
        //.setFields(SearchDocumentFieldName.allStoreTextDocumentFields) 对请求设置对象，否则无法自动赋值


        System.out.println(getResponse.getId());
        System.out.println(getResponse.isExists());
        System.out.println(getResponse.getSourceAsString());


        tc.close();

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
            dd.setRealName(getResponse.getField(SearchDocumentFieldName.FILE_REAL_NAME.getFieldName()).getValue().toString());
            //dd.setTagName();
            dd.setUserName(getResponse.getField(SearchDocumentFieldName.FILE_USER_NAME.getFieldName()).getValue().toString());
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
    public List<AutoSuggestionEntry> getAutoSuggestions(EsIndexConfig config, String queryString) {
        return null;
    }

    @Override
    public List<AutoSuggestionEntry> getAutoSuggestionsUsingTermsFacet(EsIndexConfig config, String string) {
        return null;
    }

    @Override
    public List<DocumentData> findSimilarProducts(EsIndexConfig config, String[] fields, String productId) {
        return null;
    }
}
