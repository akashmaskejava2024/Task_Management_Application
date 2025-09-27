package com.nt.cntrl;

import java.awt.PageAttributes.MediaType;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.dto.APIResponse;
import com.nt.dto.TaskRequestDTO;
import com.nt.dto.TaskResponseDTO;
import com.nt.entity.Task;
import com.nt.entity.TaskList;
import com.nt.repository.TaskListRepository;
import com.nt.repository.TaskRepository;
import com.nt.service.TaskService;

@CrossOrigin("*")
@RequestMapping("/task")
@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskListRepository listRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


	@PostMapping(value = "/add", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> addNewTask(@RequestBody TaskRequestDTO dto) {
	    logger.info("Adding new task: {}", dto);

		TaskList tasklist  = listRepository.findById(dto.getTasklistId());

		if(tasklist != null ) {

			boolean isTaskExists = taskRepository.existsByNameAndTasklist(dto.getName(),tasklist);
			if (isTaskExists) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new APIResponse(null, "Already Exists"));
			}

			TaskResponseDTO addedTask = taskService.addNewTask(dto);
			
			if (addedTask != null) {
	            logger.info("Task successfully created: {}", addedTask);
				return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(addedTask, "Success"));

			} else {
	            logger.error("Failed to create task: {}", dto);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new APIResponse(null, "Inernal Server Error"));
			}

			
		} else {
	        logger.error("TaskList not found for ID: {}", dto.getTasklistId());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(null, "TaskList not found"));
		}
		
	}

	
	@PostMapping("/done")
	public ResponseEntity<APIResponse> addTaskDone(@RequestBody TaskRequestDTO dto) {
		
	    TaskResponseDTO resDto = taskService.doneTask(dto);
	    if(resDto!=null) {
	    	return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(resDto, "Success"));
	    }
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(null	, "Failed"));

		
		
	}
	
	
	@PostMapping("/deleteCompleted")
	public ResponseEntity<APIResponse> deleteCompletedTasks(@RequestBody TaskRequestDTO dto) {
		
		boolean isDeleted = taskService.deleteCompletedTasks(dto.getTasklistId());
		
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new APIResponse(isDeleted, "Deleted Success"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(isDeleted, "Deleted Failed"));
	
		}

		
	}
}
