package com.ajit.common.exceptionhandling.exception.test;

import java.util.UUID;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ajit.common.exceptionhandling.exception.CommonException;
import com.ajit.common.exceptionhandling.misc.test.ClassThrowingException;
import com.ajit.common.exceptionhandling.util.FirstFailExceptionThreadLocalHolder;
import com.ajit.common.logging.util.MetadataHeaderThreadLocalHolder;
import com.ajit.common.logging.util.MetadataHeaderThreadLocalHolder.MetadataHeaders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/appContextTest_CustomHandler.xml")
public class CustomExceptionHandlerTest {

	@Before
	public void beforeTest(){
		MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(MetadataHeaders.X_CORRELATION_ID, UUID.randomUUID().toString());
		MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(MetadataHeaders.X_MESSAGE_ID, UUID.randomUUID().toString());
		MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(MetadataHeaders.X_SYSTEM_ID, "COMMON_EXCEPTION_HANDLING");
	}

	@After
	public void afterTest(){
		MetadataHeaderThreadLocalHolder.removeMetadataHeaderFromThreadLocal(MetadataHeaders.X_CORRELATION_ID);
		MetadataHeaderThreadLocalHolder.removeMetadataHeaderFromThreadLocal(MetadataHeaders.X_MESSAGE_ID);
		MetadataHeaderThreadLocalHolder.removeMetadataHeaderFromThreadLocal(MetadataHeaders.X_SYSTEM_ID);

		FirstFailExceptionThreadLocalHolder.removeThrowable();
	}

	@Test
	public void testSoftenAspectForCheckedException_true(){
		ClassThrowingException instance = new ClassThrowingException();
		try{
			instance.methodThrowingConcurrenModificationException();
			Assert.fail("Shouldn't reach here...");
		}catch(Exception exception){
			Assert.assertEquals(CommonException.class, exception.getClass());
			CommonException commonException = (CommonException)exception;
			Assert.assertEquals("2001", commonException.getErrorCode());
			Assert.assertEquals("Custom Handler Invoked When ConcurrenModificationException Is Thrown", commonException.getErrorMessage());
		}
	}
}
