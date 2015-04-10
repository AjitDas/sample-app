package com.ajit.common.logging.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class AppContextSingleton {
	
	private static volatile AppContextSingleton singleton;
	private ApplicationContext applicationContext;

	private AppContextSingleton(){
		// to block creating instance outside this class
		applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/common-logging-standalone.xml");
	}
	
	public static AppContextSingleton getInstance(){
		if(singleton == null){
			synchronized (AppContextSingleton.class) {
				if(singleton == null){
					singleton = new AppContextSingleton();
				}
			}
		}
		return singleton;
	}
	
	public <T> T getBeanFromApplicationContext(String beanName,Class<T> beanType){
		if(applicationContext!=null){
			return applicationContext.getBean(beanName, beanType);
		}
		return null;
	}
	
	//TODO : configure bean name so that it can be customized to return different implementation
	public LogPublisher getLogPublisher(){
		return getBeanFromApplicationContext("syncLogPublisher", LogPublisher.class);
	}
}
