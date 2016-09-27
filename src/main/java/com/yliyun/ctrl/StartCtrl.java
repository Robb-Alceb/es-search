package com.yliyun.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/9/23.
 */

@RestController
@RequestMapping("/")
public class StartCtrl {

    @RequestMapping("/hello")
    public  String hello  (){

        return "hello ";

    }

}
