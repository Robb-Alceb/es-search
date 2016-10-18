package com.yliyun.util;/**
 * Created by Administrator on 2016/10/18.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/18 - 17:52
 *
 */

@Service
public class DownloadFileService {


    @Autowired
    private RestTemplate restTemplate;


    public void download(String url) throws IOException {

        System.out.println("restTemplate:"+restTemplate);

        byte[] fileByte = restTemplate.getForObject(url, byte[].class);

        Files.write(Paths.get("download/xxx.pdf"), fileByte);

    }


}
