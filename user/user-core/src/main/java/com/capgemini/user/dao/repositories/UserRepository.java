package com.capgemini.user.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.user.dao.entity.User;
import com.capgemini.user.logging.core.Auditable;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

	/**
	 * Find the user with the given username. This method will be translated into a query using the
	 * {@link javax.persistence.NamedQuery} annotation at the {@link User} class.
	 * 
	 * @param username
	 * @return
	 */
	User queryByTheUserName(String username);

	/**
	 * Find all users with the given lastname. This method will be translated into a query by constructing it directly
	 * from the method name as there is no other query declared.
	 * 
	 * @param lastname
	 * @return
	 */
	List<User> findByLastname(String lastname);

	/**
	 * Returns all users with the given firstname. This method will be translated into a query using the one declared in
	 * the {@link Query} annotation declared one.
	 * 
	 * @param firstname
	 * @return
	 */
	@Auditable @Query("select u from User u where u.firstname = ?1")
	List<User> findByFirstname(String firstname);
	
	/**
	 * Returns all users with the given firstname. This method will be translated into a query using the one declared in
	 * the {@link Query} annotation declared one.
	 * 
	 * @param firstname
	 * @return
	 */
	@Query("select u from User u where u.username = :username")
	List<User> findByUsername(@Param("username") String userName);
	
}
