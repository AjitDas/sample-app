package com.capgemini.user.aspect;
import java.io.IOException;

public aspect SoftenAspect {
	
	declare soft : IOException+ : softenPointcut();
	
	pointcut softenPointcut() : call(* *..*(..) throws IOException+) && within(com.capgemini.user..*);
	
}