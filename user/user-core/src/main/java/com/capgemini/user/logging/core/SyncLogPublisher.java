package com.capgemini.user.logging.core;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.capgemini.user.logging.event.LogEvent;

@Component("syncLogPublisher")
public class SyncLogPublisher implements LogPublisher {
	
	@Autowired private LogEventStringConverter logEventStringConverter;
	@Autowired private UserLogger userLogger;
	
	@PostConstruct
	protected void initialize(){
		if(logEventStringConverter==null){
			logEventStringConverter = new DefaultLogEventStringConverter();
		}
		if(userLogger==null){
			userLogger = new Slf4jUserLogger();
		}
	}

	@Override
	public void publishLog(LogEvent logEvent) {
		String logEventAsString = logEventStringConverter.convertLogEventToString(logEvent);
		userLogger.log(logEventAsString);
	}

	/**
	 * @param logEventStringConverter the logEventStringConverter to set
	 */
	public void setLogEventStringConverter(
			LogEventStringConverter logEventStringConverter) {
		this.logEventStringConverter = logEventStringConverter;
	}

	/**
	 * @param userLogger the userLogger to set
	 */
	public void setUserLogger(UserLogger userLogger) {
		this.userLogger = userLogger;
	}

}
