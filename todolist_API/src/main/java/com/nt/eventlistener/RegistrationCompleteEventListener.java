package com.nt.eventlistener;

import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.nt.entity.User;
import com.nt.event.RegistrationCompleteEvent;
import com.nt.service.UserService;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	@Autowired
	private UserService userservice;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {

		User user = event.getUser();
		String token = UUID.randomUUID().toString();

		userservice.saveEmailverificationToken(user, token);

		String URL = event.getApplicationurl() + "/user/verifyRegistration?token=" + token;

		try {
			userservice.sendVerificationMail(user.getEmail(), URL);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
