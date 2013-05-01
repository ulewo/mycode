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

import org.apache.commons.httpclient.HttpException;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.ulewo.api.ApiClient;
import com.ulewo.bean.Article;
import com.ulewo.bean.ArticleList;
import com.ulewo.bean.Blog;
import com.ulewo.bean.BlogList;
import com.ulewo.bean.GroupList;
import com.ulewo.bean.LoginUser;
import com.ulewo.bean.ReArticleList;
import com.ulewo.bean.ReArticleResult;
import com.ulewo.bean.ReBlogList;
import com.ulewo.bean.ReBlogResult;
import com.ulewo.bean.UlewoVersion;
import com.ulewo.bean.User;
import com.ulewo.util.Constants;
import com.ulewo.util.StringUtils;

public class AppContext extends Application {

	public static final int NETTYPE_WIFI = 0x01;

	public static final int NETTYPE_CMWAP = 0x02;

	public static final int NETTYPE_CMNET = 0x03;

	private static HashMap<String, Object> userInfo = new HashMap<String, Object>();

	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

	/**
	 * 
	 * description: 文章列表
	 * 
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public ArticleList getArticleList(int pageIndex, boolean isRefresh)
			throws AppException {

		ArticleList list = null;
		String key = StringUtils.encodeByMD5("articlelist" + "_" + pageIndex);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = ApiClient.getArticleList(pageIndex);
				if (list != null && pageIndex == 1) {
					saveObject(list, key);
				}
			} catch (AppException e) {
				list = (ArticleList) readObject(key);
				if (list == null)
					throw e;
			}
		} else {
			list = (ArticleList) readObject(key);
			if (list == null) {
				throw AppException.network(new HttpException());
			}

		}
		return list;
	}

	/**
	 * 
	 * description: 查询文章
	 * 
	 * @param articleId
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public Article getArticle(int articleId) throws AppException {

		Article article = null;
		if (isNetworkConnected()) {
			try {
				article = ApiClient.getArticle(articleId);
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return article;
	}

	/**
	 * 
	 * description: 评论列表
	 * 
	 * @param articleId
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public ReArticleList getReArticleList(int articleId, int pageIndex,
			boolean isRefresh) throws AppException {

		ReArticleList list = null;
		if (isNetworkConnected()) {
			try {
				list = ApiClient.getReArticleList(articleId, pageIndex);
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return list;
	}

	public ReArticleResult addReArticle(String content, int articleId)
			throws AppException {

		ReArticleResult result = null;
		if (isNetworkConnected()) {
			try {
				result = ApiClient.addReArticle(content, articleId,
						getSessionId(), getUserName(), getPassword());
				if (result.isLogin()) {
					putUserInfo(Constants.SESSIONID, result.getSessionId());
				} else {
					removeUserInfo(Constants.SESSIONID);
				}
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return result;
	}

	public ReArticleResult addSubReArticle(String content, int articleId,
			String atUserId, String hide_pid) throws AppException {

		ReArticleResult result = null;
		if (isNetworkConnected()) {
			try {
				result = ApiClient.addSubReArticle(content, articleId,
						atUserId, hide_pid, getSessionId(), getUserName(),
						getPassword());
				if (result.isLogin()) {
					putUserInfo(Constants.SESSIONID, result.getSessionId());
				} else {
					removeUserInfo(Constants.SESSIONID);
				}
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return result;
	}

	/**
	 * 
	 * description: 博客列表
	 * 
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public BlogList getBlogList(int pageIndex, boolean isRefresh)
			throws AppException {

		BlogList list = null;
		String key = StringUtils.encodeByMD5("bloglist" + "_" + pageIndex);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = ApiClient.getBlogList(pageIndex);
				if (list != null && pageIndex == 1) {
					saveObject(list, key);
				}
			} catch (AppException e) {
				list = (BlogList) readObject(key);
				if (list == null)
					throw e;
			}
		} else {
			list = (BlogList) readObject(key);
			if (list == null) {
				throw AppException.network(new HttpException());
			}
		}
		return list;
	}

	/**
	 * 
	 * description: 博客详情
	 * 
	 * @param articleId
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public Blog getBlog(int articleId) throws AppException {

		Blog blog = null;
		if (isNetworkConnected()) {
			try {
				blog = ApiClient.getBlog(articleId);
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return blog;
	}

	/**
	 * 博客回复列表
	 * 
	 * @param articleId
	 * @param pageIndex
	 * @return
	 * @throws AppException
	 */
	public ReBlogList getReBlogList(int articleId, int pageIndex)
			throws AppException {
		ReBlogList list = null;
		if (isNetworkConnected()) {
			try {
				list = ApiClient.getReBlogList(articleId, pageIndex);
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return list;
	}

	/***
	 * 
	 * @param content
	 * @param articleId
	 * @return
	 * @throws AppException
	 */

	public ReBlogResult addReBlog(String content, int articleId)
			throws AppException {

		ReBlogResult result = null;
		if (isNetworkConnected()) {
			try {
				result = ApiClient.addReBlog(content, articleId,
						getSessionId(), getUserName(), getPassword());
				if (result.isLogin()) {
					putUserInfo(Constants.SESSIONID, result.getSessionId());
				} else {
					removeUserInfo(Constants.SESSIONID);
				}
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return result;
	}

	/**
	 * 
	 * description:群组列表
	 * 
	 * @param pageIndex
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public GroupList getGroupList(int pageIndex, boolean isRefresh)
			throws AppException {

		GroupList list = null;
		String key = StringUtils.encodeByMD5("grouplist" + "_" + pageIndex);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = ApiClient.getGroupList(pageIndex);
				if (list != null && pageIndex == 1) {
					saveObject(list, key);
				}
			} catch (AppException e) {
				list = (GroupList) readObject(key);
				if (list == null)
					throw e;
			}
		} else {
			list = (GroupList) readObject(key);
			if (list == null) {
				throw AppException.network(new HttpException());
			}
		}
		return list;
	}

	/**
	 * 
	 * description: 群组文章列表
	 * 
	 * @param articleId
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public ArticleList getGroupArticleList(String gid, boolean isRefresh,
			int pageIndex) throws AppException {

		ArticleList list = null;
		String key = StringUtils.encodeByMD5("groupArticlelist" + "_" + gid+pageIndex);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = ApiClient.getGroupArticleList(pageIndex, gid);
				if (list != null && pageIndex == 1) {
					saveObject(list, key);
				}
			} catch (AppException e) {
				list = (ArticleList) readObject(key);
				if (list == null)
					throw e;
			}
		} else {
			list = (ArticleList) readObject(key);
			if (list == null) {
				throw AppException.network(new HttpException());
			}
		}
		return list;
	}

	/**
	 * 
	 * description:登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws AppException
	 * @author luohl
	 */
	public LoginUser login(String userName, String password, boolean isLogin)
			throws AppException {

		LoginUser loginUser = null;
		String key = StringUtils.encodeByMD5("user");
		if (isNetworkConnected() && isLogin) {
			try {
				loginUser = ApiClient.login(userName, password);
				if (Constants.SUCCESS.equals(loginUser.getLoginResult())) {
					User user = loginUser.getUser();
					user.setPassword(password);
					loginUser.setUser(user);
					saveObject(loginUser, key);
					userInfo.put(Constants.USERNAME, userName);
					userInfo.put(Constants.PASSWORD, password);
					userInfo.put(Constants.SESSIONID, user.getSessionId());
				}
			} catch (AppException e) {
				loginUser = (LoginUser) readObject(key);
				if (loginUser == null)
					throw e;
			}
		} else {
			loginUser = (LoginUser) readObject(key);
			if (loginUser == null) {
				throw AppException.network(new HttpException());
			}
		}
		return loginUser;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 * @param isRefresh
	 * @return
	 * @throws AppException
	 */
	public User fetchUserInfo(String userId, boolean isRefresh)
			throws AppException {
		User user = null;
		String key = StringUtils.encodeByMD5(userId);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				user = ApiClient.fetchUserInfo(userId);
				if (user != null) {
					saveObject(user, key);
				}
			} catch (AppException e) {
				user = (User) readObject(key);
				if (user == null)
					throw e;
			}
		} else {
			user = (User) readObject(key);
			if (user == null) {
				throw AppException.network(new HttpException());
			}
		}
		return user;
	}

	public UlewoVersion fetchVersion() throws AppException {
		UlewoVersion version = null;
		if (isNetworkConnected()) {
			try {
				version = ApiClient.getVersion();
			} catch (AppException e) {
				throw e;
			}
		} else {
			throw AppException.network(new HttpException());
		}
		return version;
	}

	/**
	 * 判断缓存数据是否可读
	 * 
	 * @param cachefile
	 * @return
	 */
	public boolean isReadDataCache(String cachefile) {

		return readObject(cachefile) != null;
	}

	/**
	 * 判断缓存是否存在
	 * 
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile) {

		boolean exist = false;

		String sDir = Environment.getExternalStorageDirectory()
				+ Constants.SEPARATOR + Constants.ULEWO;
		File file = new File(sDir, cachefile);
		if (file.exists()) {
			exist = true;
		}
		return exist;

	}

	/**
	 * 将对象保存到内存缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {

		memCacheRegion.put(key, value);
	}

	/**
	 * 从内存缓存中获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key) {

		return memCacheRegion.get(key);
	}

	/**
	 * 保存对象
	 * 
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String fileName) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {

				String sDir = Environment.getExternalStorageDirectory()
						+ Constants.SEPARATOR + Constants.ULEWO;
				File destDir = new File(sDir);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				File file = new File(sDir, fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(ser);
				oos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != fos) {
						fos.close();
					}
				} catch (Exception e) {
				}
				try {
					if (null != oos) {
						oos.close();
					}
				} catch (Exception e) {
				}
			}
		}
		return false;
	}

	/**
	 * 读取对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String fileName) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			String sDir = Environment.getExternalStorageDirectory()
					+ Constants.SEPARATOR + Constants.ULEWO;
			try {
				File file = new File(sDir, fileName);
				if (!file.exists()) {
					return null;
				}
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				Object obj = ois.readObject();
				return (Serializable) obj;
			} catch (Exception e) {
				e.printStackTrace();
				// 反序列化失败 - 删除缓存文件
				if (e instanceof InvalidClassException) {
					File data = new File(sDir, fileName);
					data.delete();
				}
			} finally {
				try {
					ois.close();
				} catch (Exception e) {
				}
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public Serializable deleteObject(String fileName) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String sDir = Environment.getExternalStorageDirectory()
					+ Constants.SEPARATOR + Constants.ULEWO;
			File file = new File(sDir, fileName);
			if (file.exists()) {
				file.delete();
			}
		}
		return null;
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

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
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
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	public static String getSessionId() {

		Object obj = userInfo.get(Constants.SESSIONID);
		return null == obj ? null : String.valueOf(obj);
	}

	public static String getUserName() {

		Object obj = userInfo.get(Constants.USERNAME);
		return null == obj ? null : String.valueOf(obj);
	}

	public static String getPassword() {

		Object obj = userInfo.get(Constants.PASSWORD);
		return null == obj ? null : String.valueOf(obj);
	}

	public static void putUserInfo(String key, String value) {

		userInfo.put(key, value);
	}

	public static void removeUserInfo(String key) {

		if (null != userInfo.get(key)) {
			userInfo.remove(key);
		}
	}
}
