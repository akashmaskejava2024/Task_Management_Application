package com.nt.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Component
public class TaskListRequestDTO {

	private int id;
	private String listname;
	private boolean isListed;
	private String username;
	
}
