package com.capgemini.user.dao.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.capgemini.user.dao.entity.User;
import com.capgemini.user.dao.repositories.UserRepositoryCustom;

public class UserRepositoryImpl implements UserRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<User> findUserByFirstNameAndLastName(String firstName,String lastName) {
		TypedQuery<User> query = entityManager.createQuery("select u from User u where u.firstname = :firstName and u.lastname = :lastName", User.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getResultList();
	}
	
}
