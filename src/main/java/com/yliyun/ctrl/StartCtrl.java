package com.yliyun.ctrl;

import com.yliyun.index.IndexMappingBuild;
import com.yliyun.index.SetupIndexService;
import com.yliyun.search.QueryService;
import com.yliyun.search.SearchResult;
import com.yliyun.util.AppConfig;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/23.
 */

@RestController
@RequestMapping("/")
public class StartCtrl {

    @Autowired
    private QueryService queryService;


    @Autowired
    private SetupIndexService setupIndexService;

    @Autowired
    private AppConfig ac;


    @RequestMapping("/hello")
    public String hello() {

        return "hello,  I'm ok! ";

    }

    @RequestMapping(value = "/fsearch", method = RequestMethod.POST)
    public SearchResult search(@RequestBody SearchParam param ) {

        System.out.println("param:"+param.toString());

        return queryService.baseSearch(param);
    }


    @RequestMapping("/setup")
    public String setup() {

        if (setupIndexService.isIndexExists(ac.getIndexName())) {
            return "setuped";
        } else {

            if (setupIndexService.createIndex()) {

                XContentBuilder sb = IndexMappingBuild.getDocumentTypeMapping(ac.getTypeName());

                try {
                    if (setupIndexService.createMapping(sb)) {
                        return "set sucess";
                    } else {
                        return "set mapping fail";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return "setup index fail";
            }
        }
        return null;
    }


}
