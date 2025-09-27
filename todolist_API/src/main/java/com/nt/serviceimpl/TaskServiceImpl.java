package com.nt.serviceimpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.dto.TaskRequestDTO;
import com.nt.dto.TaskResponseDTO;
import com.nt.entity.Task;
import com.nt.entity.TaskList;
import com.nt.mapper.TaskMapper;
import com.nt.repository.TaskListRepository;
import com.nt.repository.TaskRepository;
import com.nt.service.TaskListService;
import com.nt.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskListRepository listRepository;

	@Autowired
	private TaskListService taskListService;

	@Autowired
	private TaskMapper taskMapper;

	@Transactional
	@Override
	public TaskResponseDTO addNewTask(TaskRequestDTO dto) {

		Task addedTask = taskRepository.save(taskMapper.toEntity(dto));
		TaskResponseDTO resDto = taskMapper.toResponseDTO(addedTask);
		return resDto;

	}

	@Override
	public TaskResponseDTO doneTask(TaskRequestDTO dto) {

		Optional<Task> opt = taskRepository.findById(dto.getId());
		Task intialTask = null;
		if (opt.isPresent()) {
			intialTask = opt.get();
		}
		
		if(intialTask!=null) {
			intialTask.setDone(true);
			Task task = taskRepository.save(intialTask);
			TaskResponseDTO resDto = taskMapper.toResponseDTO(task);
			return resDto;
		} else {
			return null;
		}

	}

	@Transactional
	@Override
	public boolean deleteCompletedTasks(int tasklistId) {
		
		  TaskList tasklist = listRepository.findById(tasklistId);		

		taskRepository.deleteByIsDoneAndTasklist(true, tasklist);
		return true;
	}

}
