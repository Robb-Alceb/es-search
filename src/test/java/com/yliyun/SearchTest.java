package com.yliyun;
/**
 * Created by Administrator on 2016/10/27.
 */

import com.yliyun.ctrl.SearchParam;
import com.yliyun.search.QueryService;
import com.yliyun.util.AppConstants;
import com.yliyun.util.Json;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/27 - 11:15
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTest {

    @Autowired
    private QueryService queryService;

    @Test
    public void testSearchId() throws Exception {

        System.out.println(queryService.getProduct(452001l).toString());
        ;

    }

    @Test
    public void testBaseSearch(){

        SearchParam sp = new SearchParam();
        sp.setLimit(20);
        sp.setOffset(0);
        sp.setKeyword("文件");
        sp.setUserId(99l);
       // sp.setDocTypoe("2");
        //sp.setFileCategory(AppConstants.FC_PUBLIC);

        try {
            System.out.println(Json.toJson( queryService.baseSearch(sp)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
