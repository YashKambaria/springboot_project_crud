package com.example.demo.entities;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data //this will automatically set getters and setters for each val
public class TaskEntity {
	private int id;
	private String title;
	private String description;
	private Date deadline;
	private boolean completed;
}
