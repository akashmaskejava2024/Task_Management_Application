package com.nt.mapper;

import org.springframework.stereotype.Component;

import com.nt.dto.TaskListRequestDTO;
import com.nt.entity.TaskList;

@Component
public class TaskListMapper {
	
	public TaskList	 toEntity(TaskListRequestDTO dto) {
		
		return new TaskList(dto.getId(), dto.getListname(), dto.isListed()	, null, null);
		
	}

}
