package com.capgemini.user.aspect;

import com.capgemini.user.annotation.NotSoften;

public aspect UserSoftenAspect{
	
	declare soft : Exception+ : softenPointcut();
	
	//pointcut softenPointcut() : call (* *..* (..) throws (java.lang.Throwable+ && !java.lang.RuntimeException+ && !java.lang.Error+)) && within(com.capgemini.user..*);
	
	pointcut softenPointcut() : (call (* *..* (..) throws (java.lang.Throwable+ && !java.lang.RuntimeException+ && !java.lang.Error+)) || 
			call (*.new(..) throws (Throwable+ && !RuntimeException+ && !Error+))) &&  
			within(com.capgemini.user..*)  /*&& !within(com.capgemini.user.logging.event..*)*/ && !within(@org.aspectj.lang.annotation.Aspect *) && !call(@NotSoften * *(..));
}