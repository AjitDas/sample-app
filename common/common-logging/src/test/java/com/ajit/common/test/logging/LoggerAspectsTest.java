package com.ajit.common.test.logging;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ajit.common.logging.util.MetadataHeaderThreadLocalHolder;
import com.ajit.common.logging.util.MetadataHeaderThreadLocalHolder.MetadataHeaders;
import com.ajit.common.test.logging.beans.User;
import com.ajit.common.test.logging.beans.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/appContextTest.xml")
public class LoggerAspectsTest {
	
	@Autowired private ApplicationContext applicationContext;
	
	@Autowired private UserService userService;
	
	@Test
	public void testCrudUserWithLogs(){
		
		MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(MetadataHeaders.X_CORRELATION_ID, UUID.randomUUID().toString());
		MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(MetadataHeaders.X_MESSAGE_ID, UUID.randomUUID().toString());
		MetadataHeaderThreadLocalHolder.putMetadatHeaderToThreadLocal(MetadataHeaders.X_SYSTEM_ID, "COMMON_LOGGING");
		
		User alreadyExist = userService.createUser(1L, "Ajit Das");
		Assert.assertNull(alreadyExist);
		
		User foundUser = userService.findUser(1L);
		Assert.assertNotNull(foundUser);
		
		User alreadyExistingUser = userService.updateUser(1L, "Ajit Amitav Das");
		Assert.assertNotNull(alreadyExistingUser);
		Assert.assertTrue(alreadyExistingUser.equals(foundUser));
		
		User foundAgainAfterUser = userService.findUser(1L);
		Assert.assertNotNull(foundAgainAfterUser);
		
		User deletedUser = userService.deleteUser(1L);
		Assert.assertNotNull(deletedUser);
		Assert.assertTrue(foundAgainAfterUser.equals(deletedUser));
	}
}
