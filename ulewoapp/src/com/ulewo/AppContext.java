package com.ulewo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ulewo.api.ApiClient;
import com.ulewo.bean.ArticleList;
import com.ulewo.common.StringUtils;
import com.ulewo.util.Constants;
import com.ulewo.util.Tools;

public class AppContext extends Application {

	public static final int NETTYPE_WIFI = 0x01;

	public static final int NETTYPE_CMWAP = 0x02;

	public static final int NETTYPE_CMNET = 0x03;

	private static HashMap<String, Object> userInfo = new HashMap<String, Object>();

	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

	public static void putUserInfo(String key, String value) {

		userInfo.put(key, value);
	}

	public ArticleList getArticleList(int pageIndex, boolean isRefresh) throws Exception {

		ArticleList list = null;
		String key = Tools.encodeByMD5("articlelist" + "_" + pageIndex);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = ApiClient.getArticleList(pageIndex);
				if (list != null && pageIndex == 0) {
					saveObject(list, key);
				}
			}
			catch (Exception e) {
				list = (ArticleList) readObject(key);
				if (list == null)
					throw e;
			}
		}
		else {
			list = (ArticleList) readObject(key);
			if (list == null)
				list = new ArticleList();
		}
		return list;
	}

	/**
	 * 判断缓存数据是否可读
	 * @param cachefile
	 * @return
	 */
	private boolean isReadDataCache(String cachefile) {

		return readObject(cachefile) != null;
	}

	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile) {

		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

	/**
	 * 判断缓存是否失效
	 * @param cachefile
	 * @return
	 */
	public boolean isCacheDataFailure(String cachefile) {

		boolean failure = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists())
			failure = true;
		else if (!data.exists())
			failure = true;
		return failure;
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
		}
		catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
	 */
	public int getNetworkType() {

		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StringUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				}
				else {
					netType = NETTYPE_CMWAP;
				}
			}
		}
		else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 将对象保存到内存缓存中
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {

		memCacheRegion.put(key, value);
	}

	/**
	 * 从内存缓存中获取对象
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key) {

		return memCacheRegion.get(key);
	}

	/**
	 * 保存磁盘缓存
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setDiskCache(String key, String value) throws IOException {

		FileOutputStream fos = null;
		try {
			fos = openFileOutput("cache_" + key + ".data", Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.flush();
		}
		finally {
			try {
				fos.close();
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 * 获取磁盘缓存数据
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getDiskCache(String key) throws IOException {

		FileInputStream fis = null;
		try {
			fis = openFileInput("cache_" + key + ".data");
			byte[] datas = new byte[fis.available()];
			fis.read(datas);
			return new String(datas);
		}
		finally {
			try {
				fis.close();
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				oos.close();
			}
			catch (Exception e) {
			}
			try {
				fos.close();
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 * 读取对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file) {

		if (!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			return (Serializable) obj;
		}
		catch (Exception e) {
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if (e instanceof InvalidClassException) {
				File data = getFileStreamPath(file);
				data.delete();
			}
		}
		finally {
			try {
				ois.close();
			}
			catch (Exception e) {
			}
			try {
				fis.close();
			}
			catch (Exception e) {
			}
		}
		return null;
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
