package com.capgemini.user.service;

import java.util.List;

import com.capgemini.user.dao.entity.User;
import com.capgemini.user.logging.core.Auditable;

public interface UserService {
	
	@Auditable
	public User createUser(User user);
	
	public User updateUser(User user);
	
	public void deleteUser(Long id);
	
	@Auditable
	public User findUserWithId(Long id);
	
	public User findUserWithUsername(String username);
	
	public List<User> findUsersWithFirstname(String firstname);
	
	public List<User> findUsersWithLastname(String lastname);
	
	public List<User> findUsersWithFirstnameAndLastname(String firstname, String lastname);

}
