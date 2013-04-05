package com.ulewo.bean;

import java.util.HashMap;
import java.util.Map;

import com.ulewo.enums.TaskType;

public class Task {
	private TaskType taskType;

	private Map<String, Object> taskParams = new HashMap<String, Object>();

	public Task(TaskType taskType, Map<String, Object> taskParams) {
		this.taskType = taskType;
		this.taskParams = taskParams;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}

}
