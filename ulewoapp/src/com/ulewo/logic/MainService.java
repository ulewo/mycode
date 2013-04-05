package com.ulewo.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.ulewo.bean.Task;
import com.ulewo.ui.IMainActivity;

public class MainService extends Service implements Runnable {

	protected static final int QUERYARTICLES_VALUE = 1;

	private static final String USERACTIVITY = "UserActivity";

	// 任务队列
	private static Queue<Task> tasks = new LinkedList<Task>();

	private static List<Activity> appActivites = new ArrayList<Activity>();

	// 是否运行
	private boolean isRun = false;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case QUERYARTICLES_VALUE:
				// 更新UI
				IMainActivity activity = (IMainActivity) getActivityByName(USERACTIVITY);
				activity.refresh(msg.obj);
				break;
			}
		}

	};

	@Override
	public void onCreate() {
		isRun = true;
		Thread thread = new Thread(this);
		thread.start();
		super.onCreate();
	}

	@Override
	public void run() {
		while (isRun) {
			Task task = null;
			if (!tasks.isEmpty()) {
				// 任务完成后，将任务从队列中移除
				task = tasks.poll();
				if (null != task) {
					doTask(task);
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
	}

	/**
	 * 添加任务
	 * 
	 * @param t
	 */
	public static void newTask(Task t) {
		tasks.offer(t);
	}

	/**
	 * 添加activity
	 */

	public static void addActivity(Activity activity) {
		appActivites.add(activity);
	}

	private Activity getActivityByName(String name) {
		if (!appActivites.isEmpty()) {
			for (Activity activity : appActivites) {
				if (null != activity) {
					if (activity.getClass().getName().contains(name)) {
						return activity;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 执行任务
	 * 
	 * @param task
	 */
	private void doTask(Task task) {
		Message msg = handler.obtainMessage();
		msg.what = task.getTaskType().getValue();
		switch (task.getTaskType()) {
		case QUERYARTICLES:// 文章列表
			msg.obj = task.getTaskParams();
			break;
		}

		handler.sendMessage(msg);
	}

	@Override
	public IBinder onBind(Intent paramIntent) {
		// TODO Auto-generated method stub
		return null;
	}

}
