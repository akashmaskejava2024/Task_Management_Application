package com.nt.mapper;


import org.springframework.stereotype.Component;

import com.nt.dto.UserRequestDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.entity.User;

@Component
public class UserMapper {

	public User toEntity(UserRequestDTO dto) {
		
		return new User(dto.getId(), dto.getName(), dto.getDob(), dto.getEmail(), dto.getPhone(), false ,dto.getUsername(), dto.getPassword(), null);
		
	}

	public UserResponseDTO toResponseDTO(User addedUser) {
		return new UserResponseDTO(addedUser.getId(), addedUser.getName(), addedUser.getDob(), addedUser.getEmail(), addedUser.getPhone(), addedUser.getUsername(),null,null, addedUser.getTasklists());
	}

	public User toEntity(UserResponseDTO dto) {
		return new User(dto.getId(), dto.getName(), dto.getDob(), dto.getEmail(), dto.getPhone(),false, dto.getUsername(), null ,null);

	}
		
}
