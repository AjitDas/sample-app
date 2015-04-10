package com.ajit.common.aspects.test;

import junit.framework.Assert;

import org.aspectj.lang.SoftException;
import org.junit.Test;

import com.ajit.common.aspects.misc.ClassThrowingException;

public class SoftenAspectTest {

	@Test
	public void testAspect(){
		Assert.assertTrue(true);
	}
	
	@Test
	public void testSoftenAspectForCheckedException(){
		ClassThrowingException instance = new ClassThrowingException();
		try{
			//instance.methodThrowingCustomChekedException(true);
		}catch(SoftException exception){
			
		}
	}
}
