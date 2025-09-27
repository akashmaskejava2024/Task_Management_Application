package com.nt.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nt.dto.TaskRequestDTO;
import com.nt.dto.TaskResponseDTO;
import com.nt.entity.Task;
import com.nt.entity.TaskList;
import com.nt.repository.TaskListRepository;

@Component
public class TaskMapper {
	@Autowired
	private TaskListRepository listRepository;

	public Task toEntity(TaskRequestDTO dto) {
		// TODO Auto-generated method stub
		return new Task(dto.getId(), dto.getName(), dto.isDone(), dto.getDesc(), dto.getDueDate(),listRepository.findById(dto.getTasklistId()));
	}

	public TaskResponseDTO toResponseDTO(Task t) {
		
		return new TaskResponseDTO(t.getId(), t.getName(), t.getDesc(), t.getDueDate(), t.isDone(), t.getTasklist().getId());
	}

}
