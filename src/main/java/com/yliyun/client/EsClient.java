package com.yliyun.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/9/23.
 */
public class EsClient {

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
