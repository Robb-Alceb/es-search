package com.yliyun.client;/**
 * Created by Administrator on 2016/10/26.
 */

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/26 - 20:49
 *
 */
public class ClientHelper {

    private Settings setting;

    private Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();

    private Map<String, Integer> ips = new HashMap<String, Integer>(); // hostname port

    private String clusterName = "elasticsearch";

    private ClientHelper() {
        init();
        //TO-DO 添加你需要的client到helper
    }

    public static final ClientHelper getInstance() {
        return ClientHolder.INSTANCE;
    }

    private static class ClientHolder {
        private static final ClientHelper INSTANCE = new ClientHelper();
    }

    /**
     * 初始化默认的client
     */
    public void init() {
        ips.put("127.0.0.1", 9300);
        setting = Settings
                .settingsBuilder()
                .put("client.transport.sniff",true)
                .put("cluster.name","elasticsearch").build();
        addClient(setting, getAllAddress(ips));
    }

    /**
     * 获得所有的地址端口
     *
     * @return
     */
    public List<InetSocketTransportAddress> getAllAddress(Map<String, Integer> ips) {
        List<InetSocketTransportAddress> addressList = new ArrayList<InetSocketTransportAddress>();
        for (String ip : ips.keySet()) {
            try {
                addressList.add(new InetSocketTransportAddress(InetAddress.getByName(ip), ips.get(ip)));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return addressList;
    }

    public Client getClient() {
        return getClient(clusterName);
    }

    public Client getClient(String clusterName) {
        return clientMap.get(clusterName);
    }

    public void addClient(Settings setting, List<InetSocketTransportAddress> transportAddress) {

//        Client client = TransportClient.builder().build()
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

        Client client =TransportClient.builder().settings(setting).build()
                .addTransportAddresses(transportAddress
                        .toArray(new InetSocketTransportAddress[transportAddress.size()]));
        clientMap.put(setting.get("cluster.name"), client);
    }
}
