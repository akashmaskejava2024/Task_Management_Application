package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nt.entity.Task;
import com.nt.entity.TaskList;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {



	boolean existsByNameAndTasklist(String name, TaskList tasklist);


	void deleteByIsDoneAndTasklist(boolean b, TaskList tasklist);

}
