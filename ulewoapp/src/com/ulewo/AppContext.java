package com.ulewo;

import java.util.HashMap;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.ulewo.util.Constants;

public class AppContext extends Application {

	private static HashMap<String, Object> userInfo = new HashMap<String, Object>();

	public static void putUserInfo(String key, String value) {
		userInfo.put(key, value);
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public static String getSessionId() {
		Object obj = userInfo.get(Constants.SESSIONID);
		return null == obj ? null : String.valueOf(obj);
	}

	public static String getUserId() {
		Object obj = userInfo.get(Constants.USERID);
		return null == obj ? null : String.valueOf(obj);
	}

	public static String getPassword() {
		Object obj = userInfo.get(Constants.PASSWORD);
		return null == obj ? null : String.valueOf(obj);
	}
}
