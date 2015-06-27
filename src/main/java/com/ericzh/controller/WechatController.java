package com.ericzh.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ericzh.model.Text;

@RestController
public class WechatController {
	final static Logger logger = Logger.getLogger(WechatController.class);
	
	private static final String token = "820730"; 
	
	@RequestMapping(value = "/wechat", method = RequestMethod.GET)
	public String validate(
			@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce,
			@RequestParam(value = "echostr") String echostr) {

		logger.info("signature=" + signature + " timestamp=" + timestamp + " nonce=" + nonce + " echostr=" + echostr);
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(timestamp);
		list.add(nonce);
		list.add(token);
		
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		//
		String data = "";
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			data += (String) it.next();
		}
		
		logger.info("data: " + data);
		String sha1 = DigestUtils.sha1Hex(data);
		
		logger.info("sha1: " + sha1);
		logger.info("signature: " + signature);
		
		if(signature.compareTo(sha1) == 0){
			logger.info("validated!");
			return echostr;
		}
		else{
			logger.info("NOT validated!");
			return "NOT validated!";
		}
	}
	
	@RequestMapping(value = "/wechat", method = RequestMethod.POST)
	public void receiveMsg(@RequestBody String postPayload){
		logger.info("msg: " + postPayload);
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Text.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			StringBuffer xmlStr = new StringBuffer(postPayload);
			Text text = (Text) jaxbUnmarshaller.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
			if(text != null){
				logger.info("text: " + text.toString());
			}
		} catch (JAXBException e) {
			logger.error("parse xml failed", e);
		}
	}
}
