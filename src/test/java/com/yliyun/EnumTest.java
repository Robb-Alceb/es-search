package com.yliyun;/**
 * Created by Administrator on 2016/10/10.
 */

import com.yliyun.client.SearchDocumentFieldName;
import org.junit.Test;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/10 - 16:11
 *
 */
public class EnumTest {

    @Test
    public  void testGetArr(){

        System.out.println(SearchDocumentFieldName.fullTextDocumentFields);

        for (int i = 0; i < SearchDocumentFieldName.fullTextDocumentFields.length; i++) {

            System.out.println(SearchDocumentFieldName.fullTextDocumentFields[i]);

        }

    }

}
