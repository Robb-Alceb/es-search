package com.yliyun.util;/**
 * Created by Administrator on 2016/10/18.
 */

import com.yliyun.model.CommonFile;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/18 - 14:18
 *
 */
public class AppConstants {

    public static String[] TABLES = {"group_file", "personal_file", "public_file"};

    public static String TABLE_GROUP_FILE = TABLES[0];
    public static String TABLE_PERSONAL_FILE = TABLES[1];
    public static String TABLE_PUBLIC_FILE = TABLES[2];

    public static BlockingQueue<CommonFile> CACHE_STORE = new ArrayBlockingQueue<CommonFile>(30);

    public static String D_RENAME = "rename";
    public static String D_FILE_UP = "fileUp";
    public static String D_ST_UP = "statusUp";

    public static String  DOWNLOAD_ADDR = "download/";



    /***
     * 取数据；
     * @return
     */
    public static CommonFile geToQueueData() {

        try {

            return CACHE_STORE.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void pushToQueueData(List<CommonFile> cfList) {

        if (cfList.size() > 0) {
            for (CommonFile commonFile : cfList) {
                try {
                    CACHE_STORE.put(commonFile);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        int rename = 0b0010;
        int fileUp = 0b0100;
        int statusUp = 0b1000;

        System.out.println(rename | fileUp);

        System.out.println(rename & 0b0111);

        Long st = new Long(4);

        System.out.println(st.byteValue() & rename);

        String[] update = new String[3];
        // System.out.println(update.toString());
        for (int i = 0; i < update.length; i++) {
            System.out.println(update[i]);


        }

    }

}
