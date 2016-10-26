package com.yliyun;/**
 * Created by Administrator on 2016/10/24.
 */

import com.yliyun.index.*;
import com.yliyun.model.CommonFile;
import com.yliyun.util.AppConfig;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/24 - 18:18
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexTest {

    @Autowired
    private SetupIndexService setupIndexService;

    @Autowired
    private AppConfig ac;

    @Autowired
    private IndexDataService indexService;


    @Test
    public void test(){

        System.out.println(setupIndexService.isIndexExists(ac.getIndexName()));



    }

    @Test
    public void testCreate(){
        System.out.println(setupIndexService.createIndex());
        ;
    }

    @Test
    public void testmapping() throws InterruptedException, ExecutionException, IOException {

        XContentBuilder sb =  IndexMappingBuild.getDocumentTypeMapping(ac.getTypeName());

        System.out.println(sb.prettyPrint());

        System.out.println(setupIndexService.createMapping(sb));

        ;
    }

    @Test
    public  void testIndex(){
        CommonFile cf = new CommonFile();
        cf.setFile_category("group");
       // cf.setTop_dept_folder("");
        cf.setFile_contents("w啊打飞机啊收到了付款啊啊数据库的风景拉屎的风景啊上的浪费啊上的浪费啊上的浪费啊上的浪费");
        cf.setFile_id(1234455l);
        cf.setFile_name("理解啊快递发咯接受对方阿三发射点.txt");
        cf.setDoc_type(1l);
        cf.setDel_status(1l);
        cf.setCreater_name("manbuzhiw");
        cf.setCreate_time("2016-10-25 10:49:08");
        cf.setDept_id(12312312l);
        cf.setGroup_id(324234l);

        indexService.indexData(cf);

    }


    @Test
    public void testUpdateIndex(){

        Map<String, Object> map = new HashMap<String, Object>();

        map.put(SearchDocumentFieldName.FILE_CATEGORY.getFieldName(),"public");

        map.put(SearchDocumentFieldName.FILE_SIZE.getFieldName(),85555L);
        map.put(SearchDocumentFieldName.FILE_UPDATE_TIME.getFieldName(),"2016-10-26 11:49:08");

        indexService.updateData(map, 1234455l);

    }

}
