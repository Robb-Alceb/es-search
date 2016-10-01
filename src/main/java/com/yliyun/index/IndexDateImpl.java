package com.yliyun.index;

import com.yliyun.client.EsClient;
import com.yliyun.model.EsIndexConfig;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/30.
 */
public class IndexDateImpl  implements  IndexData{

    TransportClient tc = EsClient.getInstance();

    @Override
    public void indexData(EsIndexConfig config, Map<String, Object> mapping) {

       // tc.prepareIndex().setSource();
        tc.prepareIndex(config.getIndexName(),config.getTypeName()).setSource(mapping).get();



    }

    @Override
    public void updateData(EsIndexConfig config, Map<String, Object> map) {

        UpdateRequest ur = new UpdateRequest();
        ur.index(config.getIndexName());
        ur.type(config.getTypeName());
        ur.id(map.get("_id").toString());
        ur.doc(map);
        tc.update(ur).actionGet();
    }

    @Override
    public void delData(EsIndexConfig config, String id) {

        DeleteResponse response  = tc.prepareDelete(config.getIndexName(),config.getTypeName(),id).get();
    }


}
