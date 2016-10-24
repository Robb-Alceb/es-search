package com.yliyun;/**
 * Created by Administrator on 2016/10/24.
 */

import com.yliyun.index.IndexMappingBuild;
import com.yliyun.index.SetupIndexService;
import com.yliyun.util.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
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


    @Test
    public void test(){

        setupIndexService.isIndexExists(ac.getIndexName());
        setupIndexService.createIndex();

    }

    @Test
    public void testCreate(){
        setupIndexService.createIndex();
    }

    public void testmapping() throws InterruptedException, ExecutionException, IOException {


        setupIndexService.createMapping(IndexMappingBuild.getDocumentTypeMapping());
    }

}
