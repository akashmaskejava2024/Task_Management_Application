package com.nt.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.dto.TaskListRequestDTO;
import com.nt.entity.TaskList;
import com.nt.entity.User;
import com.nt.mapper.TaskListMapper;
import com.nt.repository.TaskListRepository;
import com.nt.repository.UserRepository;
import com.nt.service.TaskListService;

@Service
public class TaskListServiceImlp implements TaskListService{
	
    @Autowired
    private UserRepository userRepository;

	@Autowired
	private TaskListRepository taskListRepository;
	
	@Autowired
	private TaskListMapper taskListMapper;
	
	
	@Override
	public String addNewList(TaskListRequestDTO dto) {
		
		User user = userRepository.findByUsername(dto.getUsername());
		TaskList presenttaskList = taskListRepository.findByListnameAndUser_id(dto.getListname(), user.getId());
		if(presenttaskList != null) {
			
			return "Already_Exists";
		}
	
		
		TaskList taskList = taskListMapper.toEntity(dto);
		taskList.setUser(user);
		
	    TaskList addedTaskList = taskListRepository.save(taskList);
	    if(addedTaskList != null) {
		    return "Success";
	    } else {
	    	return "Failed";
	    }
	}


	


	@Override
	public TaskList markisListedTrue(TaskListRequestDTO dto) {

		TaskList taskList = getByListnameAndUsername(dto);
		taskList.setListed(dto.isListed());
		TaskList updatedTaskList = taskListRepository.save(taskList);
		
		return updatedTaskList;
		
		
	}


	@Override
	public TaskList getByListnameAndUsername(TaskListRequestDTO dto) {
		User user = userRepository.findByUsername(dto.getUsername());
		TaskList tasklist =  taskListRepository.findByListnameAndUser_id(dto.getListname(), user.getId());
		return tasklist;
	}

	
	
}
