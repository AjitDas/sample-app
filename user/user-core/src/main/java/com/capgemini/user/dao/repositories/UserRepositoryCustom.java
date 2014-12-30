package com.capgemini.user.dao.repositories;

import java.util.List;

import com.capgemini.user.dao.entity.User;
/**
 * This interface should define methods which are not present/can't be derived in default spring data repository.
 * All custom method that are required by application should go here.
 * 
 * @author ajidas
 *
 */
public interface UserRepositoryCustom {

	public List<User> findUserByFirstNameAndLastName(String firstName, String lastName);
}
