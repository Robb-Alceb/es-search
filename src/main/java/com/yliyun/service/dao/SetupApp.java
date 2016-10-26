package com.yliyun.service.dao;/**
 * Created by Administrator on 2016/10/24.
 */

import com.yliyun.index.IndexMappingBuild;
import com.yliyun.index.SetupIndexService;
import com.yliyun.util.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/24 - 18:13
 *
 */
public class SetupApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupApp.class);

    @Autowired
    private SetupIndexService setupIndexService;

    @Autowired
    private AppConfig ac;


    public void setupApp() throws InterruptedException, ExecutionException, IOException {

        if (setupIndexService.isIndexExists(ac.getIndexName())) {
            if (setupIndexService.createIndex()) {
               if(setupIndexService.createMapping(IndexMappingBuild.getDocumentTypeMapping(ac.getTypeName()))){
                    LOGGER.info("success !  this SetupApp is complete ! ");
               }
            }
        }

    }

}
