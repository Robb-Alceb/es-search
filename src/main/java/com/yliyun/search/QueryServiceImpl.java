package com.yliyun.search;
/**
 * Created by Administrator on 2016/10/11.
 */

import com.yliyun.client.ElasticSearchReservedWords;
import com.yliyun.client.SearchDocumentFieldName;
import com.yliyun.ctrl.SearchParam;
import com.yliyun.model.CommonFile;
import com.yliyun.model.Group_member;
import com.yliyun.service.dao.FilesService;
import com.yliyun.util.AppConfig;
import com.yliyun.util.AppConstants;
import com.yliyun.util.Json;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryParser;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public SearchResult baseSearch(SearchParam param) {

        if(param.getKeyword() == null || "".equals(param.getKeyword())){
            return  null;
        }

        List<Group_member> gml = filesService.getGroupList(param.getUserId() + "");

        List<String> gids = new ArrayList<String>();

        System.out.println(gml.size());

        for (Group_member group_member : gml) {
            gids.add(group_member.getGroup_id() + "");
        }

        System.out.println(gids.size());

        Object[] joinGroups = gids.toArray();

        String parserKeyword = org.apache.lucene.queryparser.classic.QueryParser.escape(param.getKeyword());

        // 全文搜索字段,在名字和内容中搜索，包含分词
        QueryStringQueryBuilder qqb = new QueryStringQueryBuilder(parserKeyword).field(SearchDocumentFieldName.FILE_TITLE.getFieldName(), 1.1f)
                .field(SearchDocumentFieldName.FILE_CONTENTS.getFieldName(), 1.2f)
                .analyzer(ElasticSearchReservedWords.ANALYZER_IK_MAX.getText())
                .defaultOperator(QueryStringQueryBuilder.Operator.AND);

        // 部分匹配，只在文件名匹配
        QueryBuilder wildcard =   boolQuery().should(wildcardQuery(SearchDocumentFieldName.FILE_ALL_NAME.getFieldName(),"*"+parserKeyword+"*").boost(1.8f));

        QueryBuilder globQ = boolQuery().should(qqb).should(wildcard);

        // 个人文件过滤字段
        QueryBuilder filterPersonal = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_USER_ID.getFieldName(), param.getUserId()));

        // 群组文件过滤字段
        QueryBuilder filterGroup = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(), "group"))
                .must(termsQuery(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName(), joinGroups));

        QueryBuilder filterPub = boolQuery().must(termQuery(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(), "public"));

        QueryBuilder filterAll = null;

        if (param.getFileCategory() == null) {
            filterAll = boolQuery().should(filterPersonal).should(filterGroup).should(filterPub);
        } else if (param.getFileCategory().equals(AppConstants.FC_PERSONAL)) {
            filterAll = boolQuery().should(filterPersonal);
        } else if (param.getFileCategory().equals(AppConstants.FC_GROUP)) {
            filterAll = boolQuery().should(filterGroup);
        } else {
            filterAll = boolQuery().should(filterPub);
        }

        String [] allType ={"0","1","2","3","4","5","6"};

        QueryBuilder filterType  = boolQuery().must(termsQuery(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName(), allType));

        if (param.getDocType() != null) {
            filterType = boolQuery().must(termsQuery(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName(), param.getDocType()));
        }

        // QueryBuilder filterAll = boolQuery().should(filterPersonal).should(filterGroup).should(filterPub);

        QueryBuilder tqb = boolQuery()
                .must(globQ)
                .must(filterAll)
                .must(filterType)
                .must(boolQuery().must(termQuery(SearchDocumentFieldName.FILE_STATUS.getFieldName(), 0)));

        System.out.println(tqb.toString());


        SearchResponse sr = ac.getClient().prepareSearch(ac.getIndexName()).setTypes(ac.getTypeName())
                .setQuery(tqb)
                // .setPostFilter()
                // 任意搜索添加聚合
                .addAggregation(getAggregation())
                .setExplain(true)
                .setFrom(param.getOffset())
                .setSize(param.getLimit())
                .setHighlighterNoMatchSize(80)
                .setHighlighterPreTags("<em>")
                .setHighlighterPostTags("</em>")
                .addHighlightedField(SearchDocumentFieldName.FILE_CONTENTS.getFieldName(), 50)
                .execute().actionGet();

        //  System.out.println(sr.getHits().totalHits());
        Terms agg = sr.getAggregations().get("agg");

        SearchResult srs = new SearchResult();

        Map<String, Long> typeMap = new HashMap<String, Long>();
        for (Terms.Bucket entry : agg.getBuckets()) {
            String key = (String) entry.getKey(); // bucket key
            long docCount = entry.getDocCount(); // Doc count
            System.out.println("key " + key + " doc_count " + docCount);
            typeMap.put(AppConstants.getTypeName(key), docCount);
        }
        srs.setTypes(typeMap);

        List<CommonFile> list = new ArrayList<CommonFile>();

        for (SearchHit searchHit : sr.getHits()) {
            // System.out.println(searchHit.getId());

            // System.out.println(searchHit.getSourceAsString());
            // System.out.println(searchHit.getSource());

            Map<String, Object> rsMap = searchHit.getSource();

            Map<String, HighlightField> result = searchHit.highlightFields();
             System.out.println("===========高亮============="+result.get(SearchDocumentFieldName.FILE_CONTENTS.getFieldName()));
//            System.out.println("===========高亮============="+result.get(SearchDocumentFieldName.FILE_CONTENTS.getFieldName()).getFragments());
            CommonFile cf = searchRsToCom(rsMap, result);

            list.add(cf);


        }

        srs.setFileList(list);
        return srs;
    }

    private CommonFile searchRsToCom(Map<String, Object> rsMap, Map<String, HighlightField> hmap) {
        CommonFile cf = new CommonFile();
        cf.setDel_status(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_STATUS.getFieldName()).toString()));

        if (rsMap.get(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName()) != null) {
            cf.setGroup_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_GROUP_ID.getFieldName()).toString()));
        }
        cf.setFolder(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_IS_FOLDER.getFieldName()).toString()));

        if (rsMap.get(SearchDocumentFieldName.FILE_DEPT_ID.getFieldName()) != null) {

            cf.setDept_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_DEPT_ID.getFieldName()).toString()));
        }

        cf.setFile_category(rsMap.get(SearchDocumentFieldName.FILE_CATEGORY.getFieldName()).toString());
        cf.setCreate_time(rsMap.get(SearchDocumentFieldName.FILE_CREATE_TIME.getFieldName()).toString());
        cf.setCreater_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_CREATER_USER_ID.getFieldName()).toString()));
        cf.setCreater_name(rsMap.get(SearchDocumentFieldName.FILE_CREATER_USER_NAME.getFieldName()).toString());
        cf.setDoc_type(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_EXT_NAME.getFieldName()).toString()));
        cf.setFile_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_ID.getFieldName()).toString()));
        cf.setFile_name(rsMap.get(SearchDocumentFieldName.FILE_TITLE.getFieldName()).toString());
        cf.setFile_size(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_SIZE.getFieldName()).toString()));
        cf.setFs_file_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FS_FILE_ID.getFieldName()).toString()));

        if (rsMap.get(SearchDocumentFieldName.FILE_PARENT_ID.getFieldName()) != null) {
            cf.setParent_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_PARENT_ID.getFieldName()).toString()));
        }
        if (rsMap.get(SearchDocumentFieldName.FILE_USER_ID.getFieldName()) != null) {
            cf.setUser_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_USER_ID.getFieldName()).toString()));
        }
        if (rsMap.get(SearchDocumentFieldName.FILE_UPDATE_USER_NAME.getFieldName()) != null) {
            cf.setUpdate_user_name(rsMap.get(SearchDocumentFieldName.FILE_UPDATE_USER_NAME.getFieldName()).toString());
        }

        if (rsMap.get(SearchDocumentFieldName.FILE_UPDATE_USER_ID.getFieldName()) != null) {
            cf.setUpdate_user_id(Long.parseLong(rsMap.get(SearchDocumentFieldName.FILE_UPDATE_USER_ID.getFieldName())+""));
        }

        if (rsMap.get(SearchDocumentFieldName.FILE_UPDATE_TIME.getFieldName()) != null) {
            cf.setUpdate_time(rsMap.get(SearchDocumentFieldName.FILE_UPDATE_TIME.getFieldName())+"");
        }
        if (rsMap.get(SearchDocumentFieldName.FILE_CONTENTS.getFieldName()) != null) {
            cf.setFile_contents(hmap.get(SearchDocumentFieldName.FILE_CONTENTS.getFieldName())+"");
        }

        return cf;
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
