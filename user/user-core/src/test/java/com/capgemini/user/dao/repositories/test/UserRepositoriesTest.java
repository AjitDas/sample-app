package com.capgemini.user.dao.repositories.test;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
import com.capgemini.user.dao.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContextTest.xml")
public class UserRepositoriesTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRepositoriesTest.class);

	@Autowired private UserRepository userRepository;
	
	@BeforeClass
	public static void setup(){
		System.setProperty("ENV_NAME", "TEST");
	}
	
	@Test @Transactional
	public void createUser(){
		List<User> allUsersBefore = userRepository.findAll();
		User user = new User(1L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User psersistedUser = userRepository.save(user);
		Assert.assertNotNull(psersistedUser);
		
		List<User> allUsers = userRepository.findAll();
		Assert.assertTrue(allUsers.size()==(allUsersBefore.size()+1));
		
		logger.debug("Retrived Users After Persistence >>> createUser()" +allUsers);
		
		User foundUser = userRepository.findOne(1L);
		Assert.assertEquals(1L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
		
	}
	
	@Test @Transactional
	public void updateUser(){
		List<User> allUsersBefore = userRepository.findAll();
		User user = new User(2L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User psersistedUser = userRepository.save(user);
		Assert.assertNotNull(psersistedUser);
		
		List<User> allUsers = userRepository.findAll();
		Assert.assertTrue(allUsers.size()==(allUsersBefore.size()+1));
		logger.debug("Retrived Users After Persistence >>> updateUser()" +allUsers);
		
		User foundUser = userRepository.findOne(2L);
		Assert.assertEquals(2L, foundUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUser.getUsername());
		Assert.assertEquals("Ajit", foundUser.getFirstname());
		Assert.assertEquals("Das", foundUser.getLastname());
		
		foundUser.setFirstname("AjitUpdated");
		foundUser.setLastname("DasUpdated");
		
		User updatedUser = userRepository.save(foundUser);
		Assert.assertNotNull(updatedUser);
		
		List<User> allUsersAfterUpdate = userRepository.findAll();
		Assert.assertTrue(allUsersAfterUpdate.size()==(allUsersBefore.size()+1));
		
		logger.debug("Retrived Users After Persistence >>> updateUser()" +allUsersAfterUpdate);
		
		User foundUpdatedUser = userRepository.findOne(2L);
		Assert.assertEquals(2L, foundUpdatedUser.getId().longValue());
		Assert.assertEquals("ajidas", foundUpdatedUser.getUsername());
		Assert.assertEquals("AjitUpdated", foundUpdatedUser.getFirstname());
		Assert.assertEquals("DasUpdated", foundUpdatedUser.getLastname());
		
	}
	
	@Test @Transactional(value=TxType.SUPPORTS)
	public void queryUser(){
		List<User> allUsersBefore = userRepository.findAll();
		User user = new User(3L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit1");
		user.setLastname("Das1");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User psersistedUser = userRepository.save(user);
		Assert.assertNotNull(psersistedUser);
		
		List<User> allUsersByFirstNameAndLastName = userRepository.findUserByFirstNameAndLastName("Ajit1", "Das1");
		Assert.assertTrue(allUsersByFirstNameAndLastName.size()==(allUsersBefore.size()+1));
		User foundUserByFirstNameAndLastName = allUsersByFirstNameAndLastName.get(0);
		Assert.assertEquals(3L, foundUserByFirstNameAndLastName.getId().longValue());
		Assert.assertEquals("ajidas", foundUserByFirstNameAndLastName.getUsername());
		Assert.assertEquals("Ajit1", foundUserByFirstNameAndLastName.getFirstname());
		Assert.assertEquals("Das1", foundUserByFirstNameAndLastName.getLastname());
		
		User foundUserByUserName = userRepository.queryByTheUserName("ajidas");
		Assert.assertEquals(3L, foundUserByUserName.getId().longValue());
		Assert.assertEquals("ajidas", foundUserByUserName.getUsername());
		Assert.assertEquals("Ajit1", foundUserByUserName.getFirstname());
		Assert.assertEquals("Das1", foundUserByUserName.getLastname());
		
		List<User> allUsersByLastName = userRepository.findByLastname("Das1");
		Assert.assertTrue(allUsersByLastName.size()==(allUsersBefore.size()+1));
		User foundUserByLastName = allUsersByLastName.get(0);
		Assert.assertEquals(3L, foundUserByLastName.getId().longValue());
		Assert.assertEquals("ajidas", foundUserByLastName.getUsername());
		Assert.assertEquals("Ajit1", foundUserByLastName.getFirstname());
		Assert.assertEquals("Das1", foundUserByLastName.getLastname());
		
		List<User> allUsersByFirstName = userRepository.findByFirstname("Ajit1");
		Assert.assertTrue(allUsersByFirstName.size()==(allUsersBefore.size()+1));
		User foundUserByFirstName = allUsersByFirstName.get(0);
		Assert.assertEquals(3L, foundUserByFirstName.getId().longValue());
		Assert.assertEquals("ajidas", foundUserByFirstName.getUsername());
		Assert.assertEquals("Ajit1", foundUserByFirstName.getFirstname());
		Assert.assertEquals("Das1", foundUserByFirstName.getLastname());
		
		List<User> allUsersByUsername = userRepository.findByUsername("ajidas");
		Assert.assertTrue(allUsersByUsername.size()==(allUsersBefore.size()+1));
		User foundUserByUsername = allUsersByUsername.get(0);
		Assert.assertEquals(3L, foundUserByUsername.getId().longValue());
		Assert.assertEquals("ajidas", foundUserByUsername.getUsername());
		Assert.assertEquals("Ajit1", foundUserByUsername.getFirstname());
		Assert.assertEquals("Das1", foundUserByUsername.getLastname());
	}
	
	@Test @Transactional
	public void deleteUser(){
		List<User> allUsersBefore = userRepository.findAll();
		
		User user = new User(4L);
		user.setUsername("ajidas");
		user.setFirstname("Ajit");
		user.setLastname("Das");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1983);
		calendar.set(Calendar.MONTH, 06);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		user.setDob(calendar.getTime());
		
		User psersistedUser = userRepository.save(user);
		Assert.assertNotNull(psersistedUser);
		
		List<User> allUsers = userRepository.findAll();
		Assert.assertTrue(allUsers.size()==(allUsersBefore.size()+1));
		
		User foundUserById = userRepository.findOne(4L);
		Assert.assertEquals(4L, foundUserById.getId().longValue());
		Assert.assertEquals("ajidas", foundUserById.getUsername());
		Assert.assertEquals("Ajit", foundUserById.getFirstname());
		Assert.assertEquals("Das", foundUserById.getLastname());
		
		userRepository.delete(4L);
		
		List<User> allUsersAfterDelete = userRepository.findAll();
		Assert.assertTrue(allUsersAfterDelete.size()==allUsersBefore.size());
	}
}
