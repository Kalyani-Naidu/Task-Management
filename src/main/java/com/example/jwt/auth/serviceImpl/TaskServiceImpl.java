package com.example.jwt.auth.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.auth.entity.Task;
import com.example.jwt.auth.entity.Users;
import com.example.jwt.auth.exception.APIException;
import com.example.jwt.auth.exception.TaskNotFound;
import com.example.jwt.auth.exception.UserNotFound;
import com.example.jwt.auth.payload.TaskDto;
import com.example.jwt.auth.repository.TaskRepository;
import com.example.jwt.auth.repository.UserRepository;
import com.example.jwt.auth.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {
		Task task = new Task();
		TaskDto response = new TaskDto();
		BeanUtils.copyProperties(taskDto, task, "users");
		Users user = userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		task.setUsers(user);
		Task savedTask = taskRepo.save(task);
		BeanUtils.copyProperties(savedTask, response);
		return response;
	}

	@Override
	public List<TaskDto> getAllTask(long userid) {
		userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		List<Task> tasks = taskRepo.findAllByUsersId(userid);
		List<TaskDto> tasksDto = new ArrayList<>();
	    for (Task task : tasks) {
	        TaskDto taskDto = new TaskDto();
	        BeanUtils.copyProperties(task, taskDto);
	        tasksDto.add(taskDto);
	    }
		return tasksDto;
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
		Users user = userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = taskRepo.findById(taskid).orElseThrow(
				() -> new TaskNotFound(String.format("Task Id %d not found", taskid))
				);
		if(user.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid, userid));
		}
		TaskDto response = new TaskDto();
		BeanUtils.copyProperties(task, response);
		return response;
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		Users user = userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = taskRepo.findById(taskid).orElseThrow(
				() -> new TaskNotFound(String.format("Task Id %d not found", taskid))
				);
		if(user.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid, userid));
		}
		taskRepo.deleteById(taskid);
		
	}

}
