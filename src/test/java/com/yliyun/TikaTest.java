package com.yliyun;/**
 * Created by Administrator on 2016/10/14.
 */

import com.yliyun.util.TikaUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/14 - 20:49
 *
 */
public class TikaTest {

    @Test
    public  void testTike(){

        TikaUtils tu = new TikaUtils();
       // String content =  tu.fileToTxt(new File("D:\\work\\_kuaisofa\\commons.css"));

     //   System.out.println(content);


    }

    @Test
    public void getTxtFile() throws TikaException, IOException, SAXException {
        TikaUtils tu = new TikaUtils();

        tu.txtParser("D:\\work\\_kuaisofa\\commons.css");



    }
}
