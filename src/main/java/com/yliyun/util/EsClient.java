package com.yliyun.util;/**
 * Created by Administrator on 2016/10/25.
 */

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/25 - 16:00
 *
 */
public class EsClient {

    private static final Logger logger = LoggerFactory.getLogger(EsClient.class);

    private  static  String searchServerClusterName = "yliyun-es";

    private static  String IP = "127.0.0.1";

    static Map<String, String> m = new HashMap<String, String>();
    // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
    static Settings settings = Settings.settingsBuilder().put(m).put("cluster.name","yliyun-es").put("client.transport.sniff", true).build();

    // 创建私有对象
    private static TransportClient client;

    static {
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // 取得实例
    public static synchronized TransportClient getTransportClient() {
        return client;
    }
}
