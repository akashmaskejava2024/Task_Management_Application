package com.nt.cntrl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class DashBoard {
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello from server";
		
	}
	

}
