package com.actia.projects.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.actia.projects.dto.UserDto;
import com.actia.projects.entities.UserEntity;

public interface UserService extends UserDetailsService {
	
	 UserDto addUser(UserDto user);
	 UserDto updateUser(UserDto user);
	 UserDto getUserById(String id);
	 List<UserDto> getAllUsers();
	 void deleteUser(String id);
	 UserDto getUserByEmail(String email);
	void updatePassword(UserEntity  user, String newPassword);
	public List<UserEntity> getTL();
	

}
