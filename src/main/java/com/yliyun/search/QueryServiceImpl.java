package com.yliyun.search;
/**
 * Created by Administrator on 2016/10/11.
 */

import com.yliyun.client.ElasticSearchReservedWords;
import com.yliyun.client.SearchDocumentFieldName;
import com.yliyun.model.CommonFile;
import com.yliyun.model.Group_member;
import com.yliyun.service.dao.FilesService;
import com.yliyun.util.AppConfig;
import com.yliyun.util.Json;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 15:29
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private AppConfig ac;


    @Autowired
    private FilesService filesService;

    //TransportClient tc = EsClient.getInstance();


    @Override
    public SearchResult baseSearch(String keyword, String userId, int from, int size) {

        List<Group_member> gml = filesService.getGroupList(userId);

        List<String> gids = new ArrayList<String>();

        System.out.println(gml.size());

        for (Group_member group_member : gml) {
            gids.add(group_member.getGroup_id() + "");
        }

        System.out.println(gids.size());

        Object[] joinGroups =  gids.toArray();

        // 全文搜索字段
        QueryStringQueryBuilder qqb = new QueryStringQueryBuilder(keyword).field(SearchDocumentFieldName.FILE_TITLE.getFieldName(), 2.0f)
                .field(SearchDocumentFieldName.FILE_CONTENTS.getFieldName(), 0.8f)
                .analyzer(ElasticSearchReservedWords.ANALYZER_IK_MAX.getText())
                .defaultOperator(QueryStringQueryBuilder.Operator.AND);

        // 个人文件过滤字段
        QueryBuilder filterPersonal = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_USER_ID.getFieldName(), userId));

        // 群组文件过滤字段
        QueryBuilder filterGroup = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(), "group"))
                .must(termsQuery(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName(), joinGroups));

        QueryBuilder filterPub = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(), "public"));

        QueryBuilder filterAll = boolQuery().should(filterPersonal).should(filterGroup).should(filterPub);
        QueryBuilder tqb = boolQuery()
                .must(qqb)
                .must(filterAll)
                // .must(boolQuery().must(termQuery(SearchDocumentFieldName.FILE_STATUS.getFieldName(), 0)))
                ;

        System.out.println(tqb.toString());


        SearchResponse sr = ac.getClient().prepareSearch(ac.getIndexName()).setTypes(ac.getTypeName())
                .setQuery(tqb)
                // .setPostFilter()
                // 任意搜索添加聚合
                .addAggregation(getAggregation())
                .setExplain(true)
                .setFrom(from)
                .setSize(size)
                .execute().actionGet();

        System.out.println(sr.getHits().totalHits());
        Terms agg = sr.getAggregations().get("agg");

        SearchResult srs  = new SearchResult();

        System.out.println("agg----------------------: " + agg.getBuckets().size());
        String aggs = null;
        try {
            aggs = Json.toJson(agg.getBuckets());
            System.out.println("agg------------pppp----------: " + Json.toJson(agg.getBuckets()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        srs.setAgg(aggs);

        for (Terms.Bucket entry : agg.getBuckets()) {
            String key = (String) entry.getKey(); // bucket key

            long docCount = entry.getDocCount(); // Doc count
            System.out.println("key " + key + " doc_count " + docCount);

            // We ask for top_hits for each bucket
//            TopHits topHits = entry.getAggregations().get("top");
//
//            for (SearchHit hit : topHits.getHits().getHits()) {
//                System.out.println(" -> id " + hit.getId() + " _source [{}]"
//                        + hit.getSource().get("file_name"));
//            }
        }

        List<String> list = new ArrayList<String>();

        for (SearchHit searchHit : sr.getHits()) {

            System.out.println(searchHit.getId());
            System.out.println(searchHit.getSourceAsString());
            list.add(searchHit.getSourceAsString());
        }

        srs.setFileList(list);
        return srs;
    }


    protected String getStringFieldValue(SearchHit field) {
        if (field != null) {
//            return String.valueOf(field.get());
        }
        return null;
    }

    private SearchRequestBuilder AddAggregations(SearchRequestBuilder searchBuild) {

        // AggregationBuilders.terms("").field();

        return searchBuild;
    }


    @Override
    public CommonFile getProduct(Long productId) throws Exception {

        GetResponse getResponse = ac.getClient().prepareGet(ac.getIndexName(), ac.getTypeName(), productId + "")

                .setFields("_source").execute().actionGet();

        System.out.println(getResponse.getId());
        System.out.println(getResponse.isExists());
        System.out.println(getResponse.getSourceAsString());

        return Json.parse(getResponse.getSourceAsString(), CommonFile.class);
    }

    private AggregationBuilder getAggregation() {
        AggregationBuilder aggregation = AggregationBuilders
                .terms("agg")
                .field(SearchDocumentFieldName.FILE_CATEGORY.getFieldName())
                .field(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName())
                .subAggregation(
                        AggregationBuilders.topHits("top").setFrom(0)
                                .setSize(10)).size(100);
        return aggregation;
    }

    @Override
    public List<AutoSuggestionEntry> getAutoSuggestions(String queryString) {

        //SuggestBuilders.completionSuggestion("complete").field()

//        SuggestBuilders.termSuggestion("auto_suggest").field(SearchDocumentFieldName.FILE_TITLE.getFieldName()).size(10).text(queryString);

        // ac.getClient().prepareSuggest(ac.getIndexName()).addSuggestion();

        return null;
    }


}
