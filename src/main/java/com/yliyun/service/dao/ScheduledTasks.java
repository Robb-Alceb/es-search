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

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        System.out.println(ac.getDownloadUrl());

    }


    /***
     * 数据库的扫描策略
     * 当队列中数据 < 10 时进行数据添加
     */
    @Scheduled(fixedRate = 60000)
    public void scanDB() {
        log.info("The time  start scan the db : ", dateFormat.format(new Date()));

        //AppConstants.storeMap.size();

        if (AppConstants.CACHE_STORE.size() < 2) {

            List<CommonFile> cfList = filesService.getFilesList("personal_file");

            if (cfList.size() == 0) {
                cfList = filesService.getFilesList("group_file");
            }

            if (cfList.size() == 0) {
                cfList = filesService.getFilesList("public_file");
            }

            if (cfList.size() > 0) {

                AppConstants.pushToQueueData(cfList);

                log.info("pushed data in queue-----------");

            } else {
                log.info("full index  is complete!!!!");
            }

        }
    }


    @Scheduled(fixedRate = 3000)

    public void indexFile() {

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
