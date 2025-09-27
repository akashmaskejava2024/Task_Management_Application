package com.nt.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.nt.dto.UserRequestDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.entity.User;

@Service
public interface UserService {

	User addUser(UserRequestDTO dto);

	boolean checkIfSameuser(UserRequestDTO dto);

	String validateUser(UserRequestDTO dto);

	void saveEmailverificationToken(User user, String token);

	String verifyRegiastrationwithtoken(String token);



	String resendVerificationMail(String email, String applicationURL);

	void sendVerificationMail(String email, String uRL) throws MessagingException;

	UserResponseDTO getUserByUsername(String username);


	
	
	
	
	
}
