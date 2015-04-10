package com.ajit.common.test.beans;

public interface UserService {
	
	public User createUser(long userId, String userName);
	
	public User findUser(long userId);
	
	public User updateUser(long userId, String userName);
	
	public User deleteUser(long userId);

}
