package com.yliyun.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ：liushichang
 * @date ：Created in 2019/7/29 14:17
 * @description：实现war包方式加载外部配置文件
 */
public class LocalSettingsEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalSettingsEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
        LOGGER.info("LocalSettingsEnvironmentPostProcessor > start load outer config  ", AppConstants.CONFIG_OUTER_ADDR);
//        File file = new File(Paths.get(AppConstants.CONFIG_OUTER_ADDR).toUri());
        //tomcat路径
        String property = System.getProperty("catalina.home");
        LOGGER.info("catalina home:"+property);
        String path =property+File.separator+"conf"+File.separator+"application.properties";
        File file = new File(path);
        if (file.exists()) {
            LOGGER.info("LocalSettingsEnvironmentPostProcessor > start load outer config success", AppConstants.CONFIG_OUTER_ADDR);
            MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            Properties properties = loadProperties(file);
            propertySources.addFirst(new PropertiesPropertySource("Config", properties));
        }
    }

    private Properties loadProperties(File f) {
        FileSystemResource resource = new FileSystemResource(f);
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to load local settings from " + f.getAbsolutePath(), ex);
        }
    }
}
