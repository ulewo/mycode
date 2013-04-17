package com.ulewo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

import com.ulewo.api.ApiClient;
import com.ulewo.bean.Article;
import com.ulewo.bean.Blog;
import com.ulewo.bean.Group;
import com.ulewo.bean.ReArticle;
import com.ulewo.bean.RequestResult;
import com.ulewo.bean.User;
import com.ulewo.enums.PostType;
import com.ulewo.enums.ResultEnum;
import com.ulewo.util.Constants;
import com.ulewo.util.Tools;

public class Ulewo {
	private static final String BASEURL = "http://192.168.0.224:80/ulewo";

	private static final String BASEURL_ARTICLELIST = BASEURL + "/android/fetchArticle.jspx";

	private static final String BASEUR_SHOWARTICLE = BASEURL + "/android/showArticle.jspx";

	private static final String BASEUR_BLOGLIST = BASEURL + "/android/fetchBlog.jspx";

	private static final String BASEUR_SHOWBLOG = BASEURL + "/android/showBlog.jspx";

	private static final String BASEUR_GROUPLIST = BASEURL + "/android/fetchWoWo.jspx";

	private static final String BASEUR_GROUPARTICLELIST = BASEURL + "/android/fetchArticleByGid.jspx";

	private static final String BASEUR_RECOMMENT = BASEURL + "/android/fetchReComment.jspx";

	private static final String BASEUR_LOGIN = BASEURL + "/android/login.jspx";

	private static final String BASEUR_ADDCOMMENT = BASEURL + "/android/addArticleComment.jspx";

	public static final int RESULTCODE_SUCCESS = 200;

	public static final int RESULTCODE_FAIL = 400;

	private static final int NoPage = 0;

	private static final String SEPARATOR = File.separator;

	private static final String ULEWO = "ulewo";

	public static HashMap<String, Object> queryArticleList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<Article> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEURL_ARTICLELIST + "?page=" + page, page, true,
				PostType.GET, null, null);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<Article>();
					int jsonLength = jsonArray.length();
					Article article = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						//article = new Article(obj);
						list.add(article);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			}
			catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}

		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryArticle(int articleId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_SHOWARTICLE + "?articleId=" + articleId, NoPage,
				false, PostType.GET, null, null);
		Article article = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				//article = new Article(new JSONObject(response.get("obj").toString()));
				map.put("article", article);
			}
			catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryBlogList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Blog> list = null;
		String result = Constants.RESULTCODE_SUCCESS;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_BLOGLIST + "?page=" + page, page, true,
				PostType.GET, null, null);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<Blog>();
					int jsonLength = jsonArray.length();
					Blog blog = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						blog = new Blog(obj);
						list.add(blog);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			}
			catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryBlog(int articleId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_SHOWBLOG + "?articleId=" + articleId, NoPage,
				false, PostType.GET, null, null);
		Blog blog = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				blog = new Blog(new JSONObject(response.get("obj").toString()));
				map.put("article", blog);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryGroupList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<Group> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_GROUPLIST + "?page=" + page, page, true,
				PostType.GET, null, null);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<Group>();
					int jsonLength = jsonArray.length();
					Group group = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						group = new Group(obj);
						list.add(group);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			}
			catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
			}

		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryArticleListByGid(String gid, int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<Article> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_GROUPARTICLELIST + "?gid=" + gid + "&page=" + page,
				page, true, PostType.GET, null, null);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<Article>();
					int jsonLength = jsonArray.length();
					Article article = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						//article = new Article(obj);
						list.add(article);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			}
			catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 查询回复
	 * 
	 * @param page
	 * @return
	 */
	public static HashMap<String, Object> queryReCommentList(int articleId, int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<ReArticle> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_RECOMMENT + "?articleId=" + articleId + "&page="
				+ page, page, true, PostType.GET, null, null);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<ReArticle>();
					int jsonLength = jsonArray.length();
					ReArticle reArticle = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						reArticle = new ReArticle(obj);
						list.add(reArticle);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			}
			catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
			}

		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> login(String userName, String password) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("password", password);

		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_LOGIN, 0, true, PostType.POST, params, null);
		User user = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				if (null != response.get("obj")) {
					user = new User(new JSONObject(response.get("obj").toString()));
					map.put("user", user);

					// 将用户名，密码保存到sdk中，将用户名密码设置到内从中
					AppContext.putUserInfo(Constants.USERID, user.getUserName());
					AppContext.putUserInfo(Constants.PASSWORD, password);
					AppContext.putUserInfo(Constants.SESSIONID, user.getSessionId());
					String str = userName + ";" + password;
					SaveDateThread thread = new SaveDateThread(Constants.USERACCOUNT, str.getBytes());
					Thread mythread = new Thread(thread);
					mythread.start();
				}
				else {
					result = Constants.RESULTCODE_LOGINFAIL;
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> getInfoFromLocal(String userName, String password) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		HashMap<String, Object> params = new HashMap<String, Object>();
		password = Tools.encodeByMD5(password);
		params.put("userName", userName);
		params.put("password", password);

		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_LOGIN, 0, true, PostType.POST, params, null);
		User user = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				user = new User(new JSONObject(response.get("obj").toString()));
				AppContext.putUserInfo(Constants.USERID, user.getUserName());
				AppContext.putUserInfo(Constants.PASSWORD, password);
				AppContext.putUserInfo(Constants.SESSIONID, user.getSessionId());
				map.put("user", user);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> addComment(String content, int articleId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", AppContext.getUserId());
		params.put("password", AppContext.getPassword());
		params.put("sessionId", AppContext.getSessionId());
		params.put("content", content);
		params.put("articleId", articleId);

		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_ADDCOMMENT, 0, true, PostType.POST, params, null);
		ReArticle reArticle = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj.get("response")));
				reArticle = new ReArticle(new JSONObject(response.get("obj").toString()));
				map.put("reArticle", reArticle);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	static class SaveDateThread implements Runnable {
		private String url;

		private byte[] data;

		public SaveDateThread(String url, byte[] data) {

			this.url = url;
			this.data = data;
		}

		@Override
		public void run() {

			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				OutputStream out = null;
				try {
					String sDir = Environment.getExternalStorageDirectory() + SEPARATOR + ULEWO;
					File destDir = new File(sDir);
					if (!destDir.exists()) {
						destDir.mkdirs();
					}
					File file = new File(sDir, url);
					if (!file.exists()) {
						file.createNewFile();
					}
					out = new FileOutputStream(file);
					out.write(data);
					out.flush();
					out.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					try {
						if (null != out) {
							out.close();
						}
					}
					catch (Exception e) {
					}
				}
			}
		}
	}
}
