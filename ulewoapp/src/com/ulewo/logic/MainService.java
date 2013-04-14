package com.ulewo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.ulewo.Ulewo;
import com.ulewo.bean.Task;
import com.ulewo.ui.IMainActivity;

public class MainService extends Service implements Runnable {

	protected static final int QUERYARTICLES_VALUE = 1;

	protected static final int SHOWARTICLES_VALUE = 2;

	protected static final int QUERYBLOGS_VALUE = 3;

	protected static final int SHOWBLOG_VALUE = 4;

	protected static final int GROUP_VALUE = 5;

	protected static final int SHOGROUPARTICLE_VALUE = 6;

	protected static final int ARTICLECOMMENT_VALUE = 7;

	protected static final int LOGIN_VALUE = 8;

	private static final String USERACTIVITY = "UserActivity";

	// 任务队列
	private static Queue<Task> tasks = new LinkedList<Task>();

	private static List<Activity> appActivites = new ArrayList<Activity>();

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	// 是否运行
	private boolean isRun = false;

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

			HashMap<String, Object> myobj = null;
			IMainActivity activity = null;
			switch (msg.what) {
			case QUERYARTICLES_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case SHOWARTICLES_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case QUERYBLOGS_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case SHOWBLOG_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case GROUP_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case SHOGROUPARTICLE_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case ARTICLECOMMENT_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
				break;
			case LOGIN_VALUE:
				// 更新UI
				myobj = (HashMap<String, Object>) msg.obj;
				activity = (IMainActivity) myobj.get("activity");
				activity.refresh(myobj);
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
			Thread.sleep(2000);
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
	@SuppressWarnings("unchecked")
	private void doTask(Task task) {

		Message msg = handler.obtainMessage();
		msg.what = task.getTaskType().getValue();
		HashMap<String, Object> obj = null;
		HashMap<String, Object> msgMap = null;
		int page = 1;
		int articleId = 0;
		switch (task.getTaskType()) {
		case QUERYARTICLES:// 文章列表
			obj = task.getTaskParams();
			page = (Integer) obj.get("page");
			msgMap = Ulewo.queryArticleList(page);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case SHOWARTICLE:// 文章详情
			obj = task.getTaskParams();
			articleId = Integer.parseInt(obj.get("articleId").toString());
			msgMap = Ulewo.queryArticle(articleId);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case QUERYBLOGES:// 博客列表
			obj = task.getTaskParams();
			page = (Integer) obj.get("page");
			msgMap = Ulewo.queryBlogList(page);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case SHOWBLOG:// 文章详情
			obj = task.getTaskParams();
			articleId = Integer.parseInt(obj.get("articleId").toString());
			msgMap = Ulewo.queryBlog(articleId);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case GROUP:// 窝窝
			obj = task.getTaskParams();
			page = (Integer) obj.get("page");
			msgMap = Ulewo.queryGroupList(page);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case SHOWGROUP:// 窝窝文章
			obj = task.getTaskParams();
			page = (Integer) obj.get("page");
			String gid = obj.get("gid").toString();
			msgMap = Ulewo.queryArticleListByGid(gid, page);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case ARTICLECOMMENT:// 评论
			obj = task.getTaskParams();
			page = (Integer) obj.get("page");
			articleId = Integer.parseInt(String.valueOf(obj.get("articleId")
					.toString()));
			msgMap = Ulewo.queryReCommentList(articleId, page);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
			break;
		case USERINFO:// 用户信息
			obj = task.getTaskParams();
			String userName = String.valueOf(obj.get("username"));
			String password = String.valueOf(obj.get("password"));
			msgMap = Ulewo.login(userName, password);
			msgMap.put("activity", task.getCurActivity());
			msg.obj = msgMap;
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
