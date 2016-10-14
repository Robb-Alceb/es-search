package com.yliyun.ctrl;

import com.yliyun.util.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2016/9/23.
 */

@RestController
@RequestMapping("/")
public class StartCtrl {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    private  CountDownLatch latch;

//    @Autowired
//    private Receiver receiver;
//
//
//    @Autowired
//    private  CountDownLatch latch;


    @RequestMapping("/hello")
    public  String hello  (){

        stringRedisTemplate.convertAndSend("chat","Helle redis i send a msg");

//        latch.await();

       // receiver.receiveMessage();

        return "hello ";

    }

}
