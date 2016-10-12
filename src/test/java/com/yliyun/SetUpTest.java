package com.yliyun;

import com.yliyun.index.*;
import com.yliyun.model.DocumentData;
import com.yliyun.model.EsIndexConfig;
import com.yliyun.search.ForSearch;
import com.yliyun.search.ProductQueryService;
import com.yliyun.search.ProductQueryServiceImpl;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/29.
 */

public class SetUpTest {

    @Test
    public void createIndexTest(){

        SetupIndexService sis = new SetupIndexServiceImpl();

        EsIndexConfig eis = new EsIndexConfig("java2Index","javaType");

        sis.createIndex(eis);
    }

    @Test
    public void exitsIndexTest(){

        SetupIndexService sis = new SetupIndexServiceImpl();

        EsIndexConfig eis = new EsIndexConfig("java1Index","javaType");

        sis.isIndexExists(eis.getIndexName().toLowerCase());

    }

//    @Test
//    public void delIndexTest(){
//
//        SetupIndexService sis = new SetupIndexServiceImpl();
//
//        EsIndexConfig eis = new EsIndexConfig("java1Index","javaType");
//
//        sis.deleteIndex(eis.getIndexName().toLowerCase());
//
//    }


    @Test
    public void createMapping(){
        SetupIndexService sis = new SetupIndexServiceImpl();


        EsIndexConfig eis = new EsIndexConfig("java2Index","javaType");

        try {
            try {
                sis.createMapping(eis, IndexMappingBuild.getDocumentTypeMapping());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public  void  updateMapping(){

        SetupIndexService sis = new SetupIndexServiceImpl();


        EsIndexConfig eis = new EsIndexConfig("java1Index","Type");


        try {
            XContentBuilder mappingBuilder  = XContentFactory.jsonBuilder().startObject()
            .startObject("properties")
//            .startObject("id").field("type","string").field("index","not_analyzed").endObject()
//            .startObject("contents").field("type","string").field("analyzer","ik_max_word").endObject()
            .startObject("yes").field("type","string").field("analyzer","ik_max_word").endObject();

            sis.updateMapping(eis,mappingBuilder);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void indexDataTest(){

        EsIndexConfig eis = new EsIndexConfig("java1Index","Type");

        Map<String,Object> map =new HashMap<String,Object>();
        map.put("id","13145");
        map.put("footer","非法人民要上天吗？合法人则面板");
        map.put("contents","合法人群在非法人群面前显得非常物理");
        map.put("yes","ejjdff");

        IndexData id = new IndexDateImpl();
        id.indexData(eis,map);

    }

    @Test
    public void indexDataTest2(){

        EsIndexConfig eis = new EsIndexConfig("java2Index","javaType");

        DocumentData dd = new DocumentData("123457","歌曲大海-张雨他的.txt",533521,"海边，满满的消失的你，既然非常牛逼，本来模糊的脸，既然渐渐清晰，想要说声爱你",
                "pdf",new Date(),new Date(),"1","personal",false,"漫步","168891","manbu1","7884","good");


        IndexData id = new IndexDateImpl();
        id.indexData(eis,dd);

    }


    @Test
    public void searchTest(){
        ForSearch fs = new ForSearch();

        EsIndexConfig eis = new EsIndexConfig("java1Index","Type");

        fs.dosearch(eis);
    }

    @Test
    public void searchTestObj(){

        EsIndexConfig eis = new EsIndexConfig("java2Index","javaType");

        ProductQueryService pqs = new ProductQueryServiceImpl() ;

        DocumentData dd =  pqs.getProduct(eis,"12345");

        System.out.println(dd);
    }


    @Test
    public  void baseSearchTest(){
        EsIndexConfig eis = new EsIndexConfig("java2Index","javaType");

        ProductQueryService pqs = new ProductQueryServiceImpl() ;
        pqs.baseSearch(eis,"卡夫卡");

    }


}
