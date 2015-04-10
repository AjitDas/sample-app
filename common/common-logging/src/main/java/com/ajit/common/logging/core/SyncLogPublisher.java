package com.ajit.common.logging.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ajit.common.logging.event.LogEvent;

@Component("syncLogPublisher")
public class SyncLogPublisher implements LogPublisher {
	
	@Autowired private LogEventStringConverter logEventStringConverter;
	@Autowired private CommonLogger commonLogger;
	
	@PostConstruct
	protected void initialize(){
		if(logEventStringConverter==null){
			logEventStringConverter = new DefaultLogEventStringConverter();
		}
		if(commonLogger==null){
			commonLogger = new Slf4jCommonLogger();
		}
	}

	@Override
	public void publishLog(LogEvent logEvent) {
		String logEventAsString = logEventStringConverter.convertLogEventToString(logEvent);
		commonLogger.log(logEventAsString);
	}

	/**
	 * @param logEventStringConverter the logEventStringConverter to set
	 */
	public void setLogEventStringConverter(
			LogEventStringConverter logEventStringConverter) {
		this.logEventStringConverter = logEventStringConverter;
	}

	/**
	 * @param commonLogger the commonLogger to set
	 */
	public void setCommonLogger(CommonLogger commonLogger) {
		this.commonLogger = commonLogger;
	}

}
