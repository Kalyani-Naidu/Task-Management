package com.example.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.auth.payload.TaskDto;
import com.example.jwt.auth.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	//save the task
	@PreAuthorize(value = "ROLE_ADMIN")
	@PostMapping("/{userid}/tasks")
	public ResponseEntity<TaskDto> createUser(@PathVariable(name = "userid") long userid, @RequestBody TaskDto taskDto) {
		return new ResponseEntity<>(taskService.saveTask(userid, taskDto), HttpStatus.CREATED);
	}
	
	@PreAuthorize(value = "ROLE_USER")
	//get all task
	@GetMapping("/{userid}/getAllTasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable(name = "userid") long userid){
		return new ResponseEntity<List<TaskDto>>(taskService.getAllTask(userid), HttpStatus.OK);
	}
	
	//get individual task
	@PreAuthorize(value = "ROLE_USER")
	@GetMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<TaskDto> getTask(
			@PathVariable(name = "userid") long userid, 
			@PathVariable(name = "taskid") long taskid
			){
		return new ResponseEntity<TaskDto>(taskService.getTask(userid, taskid), HttpStatus.OK);
	}
	
	//delete the task
	@PreAuthorize(value = "ROLE_ADMIN")
	@DeleteMapping("/{userid}/deleteTask/{taskid}")
	public ResponseEntity<String> deleteTask(
			@PathVariable(name = "userid") long userid, 
			@PathVariable(name = "taskid") long taskid
			){
		taskService.deleteTask(userid, taskid);
		return new ResponseEntity<String>("task deleted successfully", HttpStatus.OK);
	}
	
	
}
