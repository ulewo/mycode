package com.ulewo.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ulewo.ui.LoginDialogActivity;
import com.ulewo.ui.UserCenterActivity;

public class UIHelper {
	/** 全局web样式 */
	public final static String WEB_STYLE = "<style>* {font-size:13px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:300px;} "
			+ "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;}</style>";

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

	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	public static View.OnClickListener noOnclick(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				return;
			}
		};
	}
	
	/**
	 * 获取webviewClient对象
	 * 
	 * @return
	 */
	public static WebViewClient getWebViewClient() {
		return new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				showUrlRedirect(view.getContext(), url);
				return true;
			}
		};
	}

	/**
	 * url跳转
	 * 
	 * @param context
	 * @param url
	 */
	public static void showUrlRedirect(Context context, String url) {
		openBrowser(context, url);
	}

	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url) {
		try {
			Uri uri = Uri.parse(url);
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
			ToastMessage(context, "无法浏览此网页", 500);
		}
	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	public static void Exit(final Context cont) {

	}
}
