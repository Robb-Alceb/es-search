package com.yliyun.util;/**
 * Created by Administrator on 2016/10/24.
 */

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

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


    /**
     * Created by Administrator on 2016/9/23.
     */
    public static class EsClient {

        private static  String IP = "localhost";

        private static TransportClient esc = null;

        private  EsClient(TransportClient esc) {
            this.esc = esc;
        }

        public static TransportClient getInstance (){

            if(esc == null){

                return getEsc();
            }

          return esc;

        }

        private static TransportClient getEsc() {

            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "yliyun-es").build();

            TransportClient client = null;
            try {
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP), 9300));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            // .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));


            return client;

        }


        public void close(){

            if(esc!=null){
                esc.close();
            }

        }
    }
}
