package com.ajit.common.test.beans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.ajit.common.logging.core.Auditable;

@Component("userService")
public class InMemoryUserServiceImpl implements UserService{
	
	private Map<Long, User> usersRepo = new ConcurrentHashMap<>();

	@Override @Auditable
	public User createUser(long userId, String userName) {
		User newUser = new User(userId, userName);
		return usersRepo.put(userId, newUser);
	}

	@Override @Auditable
	public User findUser(long userId) {
		return usersRepo.get(userId);
	}

	@Override @Auditable
	public User updateUser(long userId, String userName) {
		if(!usersRepo.containsKey(userId)){
			throw new IllegalArgumentException("User with id '"+ userId+"' is not present, create first");
		}
		User updateUser = new User(userId, userName);
		return usersRepo.put(userId, updateUser);
	}

	@Override @Auditable
	public User deleteUser(long userId) {
		if(!usersRepo.containsKey(userId)){
			throw new IllegalArgumentException("User with id '"+ userId+"' is not present, create first");
		}
		return usersRepo.remove(userId);
	}

}
