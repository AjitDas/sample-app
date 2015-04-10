package com.ajit.common.aspects;
import java.io.IOException;

public aspect IOExceptionSoftenAspect {
	
	declare soft : IOException+ : softenPointcut();
	
	pointcut softenPointcut() : call(* *..*(..) throws IOException+) && within(com.ajit..*);
	
}