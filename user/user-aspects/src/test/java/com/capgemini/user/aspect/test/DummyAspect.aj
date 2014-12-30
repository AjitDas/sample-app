package com.capgemini.user.aspect.test;

public aspect DummyAspect{
	
	pointcut soften() : call(* *..*(..) throws Throwable+ && !RuntimeException && !Error) && within(com.capgemini.user..*);
	
}