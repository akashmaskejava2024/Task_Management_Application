package com.nt.service;

import org.springframework.stereotype.Service;

import com.nt.dto.TaskRequestDTO;
import com.nt.dto.TaskResponseDTO;
import com.nt.entity.Task;

@Service
public interface TaskService {

	TaskResponseDTO addNewTask(TaskRequestDTO dto);

	TaskResponseDTO doneTask(TaskRequestDTO dto);

	boolean deleteCompletedTasks(int tasklistId);

}
