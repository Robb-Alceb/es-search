package com.yliyun.util;/**
 * Created by Administrator on 2016/10/18.
 */

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

    public static  ConcurrentHashMap storeMap = new ConcurrentHashMap();


    public static void main(String[] args) {

    }

}
