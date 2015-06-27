package com.ericzh;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello Eric!
 *
 */
@SpringBootApplication111
public class WechatServiceLauncher 
{
	//TODO check log level
	//TODO upload to github
	final static Logger logger = Logger.getLogger(WechatServiceLauncher.class);
    public static void main( String[] args )
    {
    	logger.info("Loading properties file ...");
    	if(ApplicationConfig.loadPropertiesFromFile("config.properties") == false){
    		logger.error("Error when load properties file");
    		System.exit(-1);
    	}
    	logger.info("Loading properties done.");
    	
    	logger.info("Wechat service is starting ...");
        SpringApplication.run(WechatServiceLauncher.class, args);
    }
}
