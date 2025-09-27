	package com.nt.dto;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nt.entity.TaskList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TaskRequestDTO {

	private int id;
	private String name;
	private boolean isDone;
	private String desc;
	private LocalDate dueDate;	
	private int tasklistId;
	private String username;

	
	
}
