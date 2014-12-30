package com.capgemini.user.service.util;

import com.capgemini.user.service.dto.User;

public class UserMapper {

	public static com.capgemini.user.dao.entity.User fromUser(User user){
		com.capgemini.user.dao.entity.User entity = null;
		if(user !=null){
			entity = new com.capgemini.user.dao.entity.User();
			entity.setId(user.getId());
			entity.setUsername(user.getUsername());
			entity.setFirstname(user.getFirstname());
			entity.setLastname(user.getLastname());
			entity.setDob(user.getDob());
		}
		return entity;
	}
	
	public static User toUser(com.capgemini.user.dao.entity.User entity){
		User dto = null;
		if(entity!=null){
			dto = new User();
			dto.setId(entity.getId());
			dto.setUsername(entity.getUsername());
			dto.setFirstname(entity.getFirstname());
			dto.setLastname(entity.getLastname());
			dto.setDob(entity.getDob());
		}
		return dto;
	}
}
