package com.nt.service;

import org.springframework.stereotype.Service;

import com.nt.dto.TaskListRequestDTO;
import com.nt.entity.TaskList;

@Service
public interface TaskListService {

	String addNewList(TaskListRequestDTO dto);


	TaskList markisListedTrue(TaskListRequestDTO dto);

	TaskList getByListnameAndUsername(TaskListRequestDTO dto);

	
}
