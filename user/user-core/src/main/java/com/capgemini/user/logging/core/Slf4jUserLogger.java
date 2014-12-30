package com.capgemini.user.logging.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("slf4jUserLogger")
public class Slf4jUserLogger implements UserLogger {

	private static final Logger logger = LoggerFactory.getLogger(Slf4jUserLogger.class);

	@Override
	public void log(String logEvent) {
		if (logEvent != null) {
			final Matcher matcher = Pattern.compile("Log Level: \\w{4,5}").matcher(logEvent);
			if (matcher.find()) {
				final String level = matcher.group().replaceAll("Log Level: ", "");
				if ("FATAL".equalsIgnoreCase(level) || "WARN".equalsIgnoreCase(level)) {
					if(logger.isWarnEnabled()){
						logger.warn(logEvent);
					}
					return;
				}else if ("ERROR".equalsIgnoreCase(level)) {
					if(logger.isErrorEnabled()){
						logger.error(logEvent);
					}
					return;
				}else if ("DEBUG".equalsIgnoreCase(level)) {
					if(logger.isDebugEnabled()){
						logger.debug(logEvent);
					}
					return;
				}else if ("TRACE".equals(level)) {
					if(logger.isTraceEnabled()){
						logger.trace(logEvent);
					}
					return;
				}
			}
			// default log with INFO
			logger.info(logEvent);
		}
	}

}
