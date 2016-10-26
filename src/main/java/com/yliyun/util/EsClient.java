package com.yliyun.util;/**
 * Created by Administrator on 2016/10/25.
 */

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    private static Client client;

    public static  Client getClient()
    {
        if(client == null)
        {
            client = createClient();
        }

        return client;
    }

    //    @PostConstruct
    protected static Client createClient()
    {
        if(client == null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Creating client for Search!");
            }
            //Try starting search client at context loading
            try
            {
                Settings settings = Settings.settingsBuilder().put("cluster.name", searchServerClusterName).build();

                TransportClient transportClient =  TransportClient.builder().settings(settings).build();

                transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP), 9300));

                if(transportClient.connectedNodes().size() == 0)
                {
                    logger.error("There are no active nodes available for the transport, it will be automatically added once nodes are live!");
                }
                client = transportClient;
            }
            catch(Exception ex)
            {
                //ignore any exception, dont want to stop context loading
                logger.error("Error occured while creating search client!", ex);
            }
        }
        return client;
    }
}
