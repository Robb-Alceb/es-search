package com.yliyun;/**
 * Created by Administrator on 2016/10/15.
 */

import com.yliyun.util.DownloadFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/15 - 15:07
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadTest {


    @Autowired
    private DownloadFileService dfs;


    @Test
    public void getTest() throws IOException {

        String url = "http://192.168.0.130/group1/M00/00/0B/wKgAglgAkpeAFhKYAAFA4xm-U2A687.pdf";

        dfs.download(url);
    }

}
