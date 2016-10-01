package com.yliyun;

import com.yliyun.index.*;
import com.yliyun.model.EsIndexConfig;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/29.
 */

public class SetUpTest {

    @Test
    public void createIndexTest(){

        SetupIndexService sis = new SetupIndexServiceImpl();

        EsIndexConfig eis = new EsIndexConfig("java1Index","javaType");

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


        EsIndexConfig eis = new EsIndexConfig("java1Index","javaType");

        try {
            sis.createMapping(eis);
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
    public void searchTest(){
        ForSearch fs = new ForSearch();

        EsIndexConfig eis = new EsIndexConfig("java1Index","Type");

        fs.dosearch(eis);
    }


}
