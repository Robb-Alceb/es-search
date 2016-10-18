package com.yliyun.service.dao;/**
 * Created by Administrator on 2016/10/15.
 */

import com.yliyun.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }


    /***
     * 数据库的扫描策略
     * 当队列中数据 < 10 时进行数据添加
     */
    @Scheduled(fixedRate = 60000)
    public void scanDB() {
        log.info("The time  start scan the db : ", dateFormat.format(new Date()));

        //AppConstants.storeMap.size();

        if(AppConstants.storeMap.size() < 10){

            System.out.println( "do scan db date ---------");

        }
    }


    @Scheduled(fixedRate = 100000)
    public void indexFile() {
        log.info("The time start  indexFile  scan the db : ", dateFormat.format(new Date()));

        //AppConstants.storeMap.size();

        if(AppConstants.storeMap.size()>0){
            System.out.println( "do index date ---------");

        }
    }



}
