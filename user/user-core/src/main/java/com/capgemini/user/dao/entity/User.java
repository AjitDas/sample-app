package com.capgemini.user.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "User.queryByTheUserName", query = "select u from User u where u.username = ?1")
public class User /*extends AbstractPersistable<Long>*/ implements Serializable{

	private static final long serialVersionUID = -2952735933715107252L;

	@Id @Column(name="id") 
	private Long id;
	@Column(name="usr_nm"/*, unique = true*/) 
	private String username;
	@Column(name="fst_nm") 
	private String firstname;
	@Column(name="lst_nm") 
	private String lastname;
	@Column(name="dob_dt")
	private Date dob;

	public User() {
		this(null);
	}

	/**
	 * Creates a new user instance.
	 */
	public User(Long id) {
		this.setId(id);
		this.id = id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s userName: %s firstName: %s lastName: %s dob: %s", 
				this.getClass().getName(), getId(), getUsername(), getFirstname(), getLastname(), getDob());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		User that = (User) obj;
		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}
	
	/**
	 * Returns the username.
	 * 
	 * @return
	 */
	public String getUsername() {

		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
}
