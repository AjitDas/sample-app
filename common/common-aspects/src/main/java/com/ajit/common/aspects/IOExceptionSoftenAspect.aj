package com.ajit.common.aspects;
import java.io.IOException;
import com.ajit.common.aspects.annotation.NotSoften;

public aspect IOExceptionSoftenAspect {
	
	declare soft : IOException+ : softenPointcut();
	
	pointcut softenPointcut() : call(* *..*(..) throws IOException+) && within(com.ajit..*) && !call(@NotSoften * *(..));
	
}