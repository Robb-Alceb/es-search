package com.yliyun.util;/**
 * Created by Administrator on 2016/10/18.
 */

import com.yliyun.model.CommonFile;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static String[] FC = {"personal", "group", "public"};
    public static String FC_PERSONAL = FC[0];
    public static String FC_GROUP = FC[1];
    public static String FC_PUBLIC = FC[2];

    public static int INDEX_FAIL = 64;

    public static String TABLE_GROUP_FILE = TABLES[0];
    public static String TABLE_PERSONAL_FILE = TABLES[1];
    public static String TABLE_PUBLIC_FILE = TABLES[2];

    public static BlockingQueue<CommonFile> CACHE_STORE = new ArrayBlockingQueue<CommonFile>(150);

    public static String D_RENAME = "rename";
    public static String D_FILE_UP = "fileUp";
    public static String D_ST_UP = "statusUp";

    public static String DOWNLOAD_ADDR = "download/";

    public static String[] docs = {"ppt", "pptx", "doc", "docx", "wps", "pps", "pptm", "xls",
            "xlsx", "xlt", "xltm", "xlsm", "xlts", "xlw", "vsd", "vsdx", "dot", "pdf", "rtf", "dwg", "dxf", "html", "htm"
            , "txt", "md"};

    public static Map<String, Integer> typeMap = new HashMap<String, Integer>();

    static {

        for (int i = 0; i < docs.length; i++) {
            typeMap.put(docs[i], 1);
        }
    }


    /***
     * 取数据；
     * @return
     */
    public static CommonFile getToQueueData() throws InterruptedException {
        return CACHE_STORE.take();
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

    public static String getTypeName(String number) {

        String[] typeArr = {"other", "pic", "doc", "audio", "video", "app", "zip"};

        return typeArr[Integer.parseInt(number)];
    }

    public static String getFileExt(String name) {
        int flag = name.lastIndexOf(".");
        if (flag > 0) {
            return name.substring(flag + 1);
        } else {
            return "**";
        }
    }


}
