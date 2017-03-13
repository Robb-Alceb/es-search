package com.yliyun;

import com.yliyun.util.AppConfig;
import com.yliyun.util.RestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

@EnableScheduling
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class ForesoneApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForesoneApplication.class);


	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx =	SpringApplication.run(ForesoneApplication.class, args);



//		ctx.getBean(RedisConfig.class);

//		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		//CountDownLatch latch = ctx.getBean(CountDownLatch.class);

		//LOGGER.info("Sending message...");
//		System.out.println("sending msg-------------------------------------------------------------");
//		template.convertAndSend("yliyun.file.upload.after", "Hello from Redis!");
//		template.convertAndSend("yliyun.file.upload.after", "Hello from Redis!-----------");

		//latch.await();

		//System.exit(0);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		// todo 创建索引资源

		return application.sources(ForesoneApplication.class);
	}


}
