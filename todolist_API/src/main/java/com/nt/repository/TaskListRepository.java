package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList	, Integer> {

	
	TaskList findByListname(String listname);

	TaskList findByListnameAndUser_id(String listname, int id);

	TaskList findById(int id);
}

