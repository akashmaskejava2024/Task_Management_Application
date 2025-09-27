package com.nt.event;


import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.nt.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent{
	

	private final User user;
	private final String applicationurl;

	public RegistrationCompleteEvent(User user, String applicationurl) {
		super(user);
		this.user= user;
		this.applicationurl = applicationurl;
		
	}

	
	
}
