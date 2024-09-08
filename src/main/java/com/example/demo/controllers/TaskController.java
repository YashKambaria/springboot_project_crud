package com.example.demo.controllers;

import com.example.demo.dto.TaskResponseDTO;
import com.example.demo.entities.TaskEntity;
import com.example.demo.service.NotesService;
import com.example.demo.service.TaskService;
import com.example.demo.dto.CreateTaskDTO;
import com.example.demo.dto.ErrorResponseDTO;
import com.example.demo.dto.UpdateTaskDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private final TaskService taskService;
	private NotesService notesService;
	private ModelMapper modelMapper =new ModelMapper();
	
	public TaskController(TaskService taskService, NotesService notesService) {
		this.taskService = taskService;
		this.notesService = notesService;
	}
	


	
	
	@GetMapping("")
	public ResponseEntity<List<TaskEntity>> getTasks(){
		var tasks=taskService.getTasks();
		
		return ResponseEntity.ok(tasks);
	}
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") Integer id){
		var notes=notesService.getNotesForTask(id);
		var task=taskService.getTaskById(id); 
		if(task==null){
			return ResponseEntity.notFound().build();
		}
		var taskResponse=modelMapper.map(task, TaskResponseDTO.class);
		taskResponse.setNotes(notes);
		return ResponseEntity.ok(taskResponse);
	}
	@PostMapping("")
	public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
		var task=taskService.addTask(body.getTitle(),body.getDescription(),body.getDeadline());
		return ResponseEntity.ok(task);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException {
		var task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());
		
		if (task == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(task);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e){
		if(e instanceof ParseException){
			return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid date format"));
		}
		e.printStackTrace();
		 return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
		
			
	}

}
