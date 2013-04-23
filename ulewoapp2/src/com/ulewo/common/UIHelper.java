package com.ulewo.common;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ulewo.ui.LoginDialogActivity;
import com.ulewo.ui.UserCenterActivity;

public class UIHelper {

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

	public static void showUserCenter(Context context, String userId,
			String userName) {
		Intent intent = new Intent(context, UserCenterActivity.class);
		intent.putExtra("userId", userId);
		intent.putExtra("userName", userName);
		context.startActivity(intent);
	}

	/**
	 * 显示登录页面
	 * 
	 * @param activity
	 */
	public static void showLoginDialog(Context context) {
		Intent intent = new Intent(context, LoginDialogActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	public static void Exit(final Context cont) {

	}
}
