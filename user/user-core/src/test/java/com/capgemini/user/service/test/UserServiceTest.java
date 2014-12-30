package com.capgemini.user.service.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.user.dao.entity.User;
import com.capgemini.user.exception.UserException;
import com.capgemini.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContextTest.xml")
public class UserServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Autowired private UserService userService;
	
	@BeforeClass
	public static void setup(){
		System.setProperty("ENV_NAME", "TEST");
	}
	
	@Test
	public void createUser(){
		User user = new User(5L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		logger.debug("Retrived Users After Persistence >>> createUser()" +createdUser);
		
		User foundUser = userService.findUserWithId(5L);
		Assert.assertEquals(5L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
	}
	
	@Test
	public void updateUser(){
		User user = new User(6L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		User foundUser = userService.findUserWithId(6L);
		Assert.assertEquals(6L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
		
		foundUser.setFirstname("AjitUpdated");
		foundUser.setLastname("DasUpdated");
		
		User updatedUser = userService.updateUser(foundUser);
		Assert.assertNotNull(updatedUser);
		
		User foundUserAfterUpdate = userService.findUserWithId(6L);
		Assert.assertEquals(6L, foundUserAfterUpdate.getId().longValue());
		Assert.assertEquals("ajidas", foundUserAfterUpdate.getUsername());
		Assert.assertEquals("AjitUpdated", foundUserAfterUpdate.getFirstname());
		Assert.assertEquals("DasUpdated", foundUserAfterUpdate.getLastname());
	}
	
	@Test
	public void deleteUser(){
		User user = new User(7L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		User foundUser = userService.findUserWithId(7L);
		Assert.assertEquals(7L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
		
		userService.deleteUser(7L);
		User deletedUser = userService.findUserWithId(7L);
		Assert.assertNull(deletedUser);
	}
	
	@Test
	public void findUserWithUsername(){
		User user = new User(8L);
		user.setUsername("dasa008");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		User foundUser = userService.findUserWithUsername("dasa008");
		Assert.assertEquals(8L, foundUser.getId().longValue());
		Assert.assertEquals("dasa008", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
	}
	
	@Test
	public void findUsersWithFirstname(){
		User user = new User(9L);
		user.setUsername("ajidas");
		user.setFirstname("AjitAmitav");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		User foundUser = userService.findUsersWithFirstname("AjitAmitav").get(0);
		Assert.assertEquals(9L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("AjitAmitav", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
	}
	
	@Test
	public void findUsersWithLastname(){
		User user = new User(10L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("DAS");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		User foundUser = userService.findUsersWithLastname("DAS").get(0);
		Assert.assertEquals(10L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("DAS", foundUser.getLastname());
	}
	
	@Test
	public void findUsersWithFirstnameAndLastname(){
		User user = new User(10L);
		user.setUsername("ajidas");
		user.setFirstname("AJIT");
		user.setLastname("DAS");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User createdUser = userService.createUser(user);
		Assert.assertNotNull(createdUser);
		
		User foundUser = userService.findUsersWithFirstnameAndLastname("AJIT","DAS").get(0);
		Assert.assertEquals(10L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("AJIT", foundUser.getFirstname());
		Assert.assertEquals("DAS", foundUser.getLastname());
	}
	
	@Test
	public void verifyUserExceptionThrown(){
		User invalidUser = null;
		try{
			userService.findUserWithId(-1L);
			Assert.fail("Shouldn't come here, UserException should have been thrown above");
		}catch(Exception exception){
			if(!(exception instanceof UserException)){
				Assert.fail("UserException should have been thrown, not other exception expected");
			}
			UserException userException = (UserException)exception;
			Assert.assertEquals("2001", userException.getErrorCode());
			Assert.assertEquals("Custom IllegalArgumentException Message", userException.getErrorMessage());
		}
		Assert.assertNull(invalidUser);
	}
	
}
