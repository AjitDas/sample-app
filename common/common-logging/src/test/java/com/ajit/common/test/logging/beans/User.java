package com.ajit.common.test.logging.beans;

import java.io.Serializable;

public class User implements Serializable{

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	private long userId;
	private String userName;
	
	public User(long userId, String userName){
		this.userId = userId;
		this.userName = userName;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}
	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
}
