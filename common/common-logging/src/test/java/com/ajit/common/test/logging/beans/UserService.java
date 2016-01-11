package com.ajit.common.test.logging.beans;

import com.ajit.common.test.logging.beans.User;

public interface UserService {
	
	public User createUser(long userId, String userName);
	
	public User findUser(long userId);
	
	public User updateUser(long userId, String userName);
	
	public User deleteUser(long userId);

}
