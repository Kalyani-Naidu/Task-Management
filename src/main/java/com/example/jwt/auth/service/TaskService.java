package com.example.jwt.auth.service;

import java.util.List;

import com.example.jwt.auth.payload.TaskDto;

public interface TaskService {
	
	public TaskDto saveTask(long userid, TaskDto taskDto);
	
	public List<TaskDto> getAllTask(long userid);
	
	public TaskDto getTask(long userid, long taskid);
	
	public void deleteTask(long userid, long taskid);
	
	

}
