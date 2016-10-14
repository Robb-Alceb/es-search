package com.yliyun.util;/**
 * Created by Administrator on 2016/10/13.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/13 - 21:33
 * 接收
 */


public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private CountDownLatch latch;


    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }



    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        System.out.println("Received------------------------------ <" + message + ">");
        latch.countDown();
    }
}
