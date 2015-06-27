package com.ericzh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationConfig {
	final static Logger logger = Logger.getLogger(ApplicationConfig.class);
	
	private static String token;
	private static String appId;
	private static String appSecret;
	
	/**
	 * load properties from file
	 * And also check if any of them is empty
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean loadPropertiesFromFile(String filePath){
		if(filePath == null || filePath.isEmpty()){
			logger.debug("filePath is empty");
			return false;
		}
		File propertiesFile = new File(filePath);
		if(propertiesFile.isFile() == false){
			logger.debug("file (" + filePath + ") is not a file");
			return false;
		}
		
		
		// load a properties file
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(propertiesFile);
			prop.load(input);

			//get properties value
			token = prop.getProperty("token");
			appId = prop.getProperty("appId");
			appSecret = prop.getProperty("appSecret");
			
			logger.debug("Got properties from file: token=" + token + ", appId=" + appId + ", appSecret=" + appSecret);
			if(checkProperties() == false){
				logger.debug("Properties are invalid!");
				return false;
			}
			
			return true;
		} catch (FileNotFoundException e) {
			logger.debug("file (" + filePath + ") not found", e);
			return false;
		} catch (IOException e) {
			logger.debug("IO error", e);
			return false;
		}
		
	}
	
	/**
	 * check if any of properties is empty
	 * @return
	 */
	private static boolean checkProperties() {
		if(token == null || token.isEmpty())
			return false;
		if(appId == null || appId.isEmpty())
			return false;
		if(appSecret == null || appSecret.isEmpty())
			return false;
		return true;
	}

	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		ApplicationConfig.token = token;
	}
	public static String getAppId() {
		return appId;
	}
	public static void setAppId(String appId) {
		ApplicationConfig.appId = appId;
	}
	public static String getAppSecret() {
		return appSecret;
	}
	public static void setAppSecret(String appSecret) {
		ApplicationConfig.appSecret = appSecret;
	}
	
}
