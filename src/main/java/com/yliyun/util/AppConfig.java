package com.yliyun.util;/**
 * Created by Administrator on 2016/10/24.
 */

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/24 - 16:09
 *
 */
@Configuration
public class AppConfig {

    @Value("${app.download.host}")
    private String downloadUrl;

    @Value("${app.index.name}")
    private String indexName;

    @Value("${app.index.type}")
    private String typeName;

    @Value("${app.es.host}")
    private String esHost;

    @Value("${app.index.dbpage}")
    private int dbPage;


//    private TransportClient client;


    public int getDbPage() {
        return dbPage;
    }

    public void setDbPage(int dbPage) {
        this.dbPage = dbPage;
    }

    public  String  getEsHost() {
        return esHost;
    }

    public void setEsHost(String esHost) {
        this.esHost = esHost;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TransportClient getClient(){
        return EsClient.getTransportClient();
    }


//    public TransportClient getClient() {
//
//        if (this.client != null) {
//            return client;
//        }
//        try {
//            Settings settings = Settings.settingsBuilder().put("cluster.name", "yliyun-es")
//                    .build();
//            client = TransportClient.builder().settings(settings).build()
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(this.getEsHost()), 9300));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        return client;
//
//    }

//    public  void close(){
//
//        client.close();
//    }






}
