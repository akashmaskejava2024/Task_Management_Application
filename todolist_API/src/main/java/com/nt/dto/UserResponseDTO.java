package com.nt.dto;

import java.time.LocalDate;
import java.util.List;

import com.nt.entity.TaskList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor



public class UserResponseDTO {

	
	private int id;
	private String name;
	private LocalDate dob;
	private String email;
	private long phone;
	private String username;
	private String msg;
	private String token;
    private List<TaskList> tasklists;

	
}
