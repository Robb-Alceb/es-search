package com.yliyun;/**
 * Created by Administrator on 2016/10/27.
 */

import com.yliyun.search.QueryService;
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
        queryService.baseSearch("会务","98",0,20);
    }


}
