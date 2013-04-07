package com.ulewo.bean;

import java.util.HashMap;

import android.app.Activity;

import com.ulewo.enums.TaskType;

public class Task {
	private TaskType taskType;

	private HashMap<String, Object> taskParams = new HashMap<String, Object>();

	private Activity curActivity;

	public Task(TaskType taskType, HashMap<String, Object> taskParams, Activity curActivity) {

		this.taskType = taskType;
		this.taskParams = taskParams;
		this.curActivity = curActivity;
	}

	public TaskType getTaskType() {

		return taskType;
	}

	public void setTaskType(TaskType taskType) {

		this.taskType = taskType;
	}

	public HashMap<String, Object> getTaskParams() {

		return taskParams;
	}

	public void setTaskParams(HashMap<String, Object> taskParams) {

		this.taskParams = taskParams;
	}

	public Activity getCurActivity() {

		return curActivity;
	}

	public void setCurActivity(Activity curActivity) {

		this.curActivity = curActivity;
	}

}
