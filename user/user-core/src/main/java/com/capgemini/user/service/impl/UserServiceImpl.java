package com.capgemini.user.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.user.dao.entity.User;
import com.capgemini.user.dao.repositories.UserRepository;
import com.capgemini.user.logging.core.Auditable;
import com.capgemini.user.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;
	
	@Autowired private MiscDummyService miscDummyService;
	
	@Override @Transactional @Auditable
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override @Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override @Transactional
	public void deleteUser(Long id) {
		userRepository.delete(id);
	}
	
	@Override @Transactional(value=TxType.SUPPORTS)
	public User findUserWithId(Long id) {
		if(id <= 0){
			throw new IllegalArgumentException(String.format("User id can't be negative, use a valid (positive) value, passed value is %s",id));
		}

		return userRepository.findOne(id);
	}

	@Override @Transactional(value=TxType.SUPPORTS)
	public User findUserWithUsername(String username) {
		// validate and in case of invalid data an exception is thrown after which exception aspect come into picture 
		miscDummyService.checkUserNameNotNullOrEmpty(username);
		
		return userRepository.searchByUserName(username);
	}

	@Override @Transactional(value=TxType.SUPPORTS)
	public List<User> findUsersWithFirstname(String firstname) {
		return userRepository.findByFirstname(firstname);
	}

	@Override @Transactional(value=TxType.SUPPORTS)
	public List<User> findUsersWithLastname(String lastname) {
		return userRepository.findByLastname(lastname);
	}

	@Override @Transactional(value=TxType.SUPPORTS)
	public List<User> findUsersWithFirstnameAndLastname(String firstname, String lastname) {
		return userRepository.findUserByFirstNameAndLastName(firstname, lastname);
	}


}
