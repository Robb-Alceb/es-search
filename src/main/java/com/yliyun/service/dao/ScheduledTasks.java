package com.yliyun.service.dao;
/**
 * Created by Administrator on 2016/10/15.
 */

import com.yliyun.model.CommonFile;
import com.yliyun.util.AppConfig;
import com.yliyun.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/15 - 16:12
 *
 */

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    private AppConfig ac;

    @Autowired
    private FilesService filesService;


    @Autowired
    private IndexTask indexTask;


    /***
     * 数据库的扫描策略
     * 当队列中数据 < 10 时进行数据添加
     */
    @Scheduled(fixedRate = 20000)
    public void scanDB() {
        log.info("The time  start scan the db : ", dateFormat.format(new Date()));

        if (AppConstants.CACHE_STORE.size() < 2) {
            scanDb();
        } else {
            log.info("The time  this index queue have   -----------  ", AppConstants.CACHE_STORE.size(), "---waiting-----");
        }


    }


    @Scheduled(fixedRate = 2000)
    public void indexFile() {
        indexTask();
    }

    private void scanDb() {

        // 索引已经更新的数据
        for (int i = 0; i < AppConstants.TABLES.length; i++) {

            List<CommonFile> upFileList = filesService.getUpFilesList(AppConstants.TABLES[i]);

            System.out.println(upFileList);

            if (upFileList.size() > 0) {
                AppConstants.pushToQueueData(upFileList);
            }
        }

        // 索引新的数据
        for (int i = 0; i < AppConstants.TABLES.length; i++) {

            List<CommonFile> cfFileList = filesService.getFilesList(AppConstants.TABLES[i]);

           // System.out.println(cfFileList);

            if (cfFileList.size() > 0) {

                AppConstants.pushToQueueData(cfFileList);
            }

        }
    }


    private void indexTask() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> : " + AppConstants.CACHE_STORE.size());
        if (AppConstants.CACHE_STORE.size() > 0) {

            log.info("The time start  indexFile --------", AppConstants.CACHE_STORE.size());

            try {
                indexTask.analysisAComFile();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            log.info("wait----------------");
        }
    }


}
