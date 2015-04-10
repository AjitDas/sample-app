package com.ajit.common.aspects;

import com.ajit.common.aspects.annotation.NotSoften;

public aspect SoftenAspect{
	
	declare soft : Exception+ : softenPointcut();
	
	//pointcut softenPointcut() : call (* *..* (..) throws (java.lang.Throwable+ && !java.lang.RuntimeException+ && !java.lang.Error+)) && within(com.capgemini.user..*);
	
	pointcut softenPointcut() : (call (* *..* (..) throws (java.lang.Throwable+ && !java.lang.RuntimeException+ && !java.lang.Error+)) || call (*.new(..) throws (Throwable+ && !RuntimeException+ && !Error+))) &&  
			within(com.ajit..*)  && 
			!within(@org.aspectj.lang.annotation.Aspect *) && 
			!call(@NotSoften * *(..));
}