package com.nt.cntrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.dto.APIResponse;
import com.nt.dto.TaskListRequestDTO;
import com.nt.entity.TaskList;
import com.nt.service.TaskListService;

@CrossOrigin("*")
@RequestMapping("/list")
@RestController
public class TaskListController {
	
	@Autowired
	private TaskListService taskListService;

	@PostMapping("/add")
	public ResponseEntity<APIResponse> addList(@RequestBody TaskListRequestDTO dto) {
		
		
		String result = taskListService.addNewList(dto);
		TaskList tasklist = taskListService.getByListnameAndUsername(dto);

		if(result.equalsIgnoreCase("Success")) {
			return ResponseEntity.ok(new APIResponse(tasklist, "SuccessFully Created List"));
			
		} else if(result.equals("Failed")){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(null, "Some Error"));
			 
			
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new APIResponse(tasklist,"Already Exists"));
		}
	}

	
	
	
	@PostMapping("/changeisListed")
	public ResponseEntity<APIResponse> listTasklistisListedTrue(@RequestBody TaskListRequestDTO dto ) {
		
	    TaskList tasklist =	taskListService.markisListedTrue(dto);
	    if(tasklist != null) {
		    return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(tasklist, "Success"));
        } else {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(null, "Failed"));
        }
		
	}
}
