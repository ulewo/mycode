package com.ulewo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.api.ApiClient;
import com.ulewo.bean.Article;
import com.ulewo.bean.Blog;
import com.ulewo.bean.Group;
import com.ulewo.bean.RequestResult;
import com.ulewo.enums.ResultEnum;
import com.ulewo.util.Constants;

public class Ulewo {
	private static final String BASEURL = "http://192.168.2.224:8080/ulewo";

	private static final String BASEURL_ARTICLELIST = BASEURL
			+ "/android/fetchArticle.jspx";

	private static final String BASEUR_SHOWARTICLE = BASEURL
			+ "/android/showArticle.jspx";

	private static final String BASEUR_BLOGLIST = BASEURL
			+ "/android/fetchBlog.jspx";

	private static final String BASEUR_SHOWBLOG = BASEURL
			+ "/android/showBlog.jspx";

	private static final String BASEUR_GROUPLIST = BASEURL
			+ "/android/fetchWoWo.jspx";

	private static final String BASEUR_GROUPARTICLELIST = BASEURL
			+ "/android/fetchArticleByGid.jspx";

	public static final int RESULTCODE_SUCCESS = 200;

	public static final int RESULTCODE_FAIL = 400;

	private static final int NoPage = 0;

	public static HashMap<String, Object> queryArticleList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<Article> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(
				BASEURL_ARTICLELIST + "?page=" + page, page, true);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response
						.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<Article>();
					int jsonLength = jsonArray.length();
					Article article = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						article = new Article(obj);
						list.add(article);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			} catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}

		} else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryArticle(int articleId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_SHOWARTICLE
				+ "?articleId=" + articleId, NoPage, false);
		Article article = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				article = new Article(new JSONObject(response.get("obj")
						.toString()));
				map.put("article", article);
			} catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}
		} else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryBlogList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Blog> list = null;
		String result = Constants.RESULTCODE_SUCCESS;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_BLOGLIST
				+ "?page=" + page, page, true);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response
						.get("list")));
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
			} catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}
		} else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryBlog(int articleId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_SHOWBLOG
				+ "?articleId=" + articleId, NoPage, false);
		Blog blog = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				blog = new Blog(new JSONObject(response.get("obj").toString()));
				map.put("article", blog);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryGroupList(int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<Group> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_GROUPLIST
				+ "?page=" + page, page, true);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response
						.get("list")));
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
			} catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
			}

		} else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}

	public static HashMap<String, Object> queryArticleListByGid(String gid,
			int page) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = Constants.RESULTCODE_SUCCESS;
		List<Article> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(
				BASEUR_GROUPARTICLELIST + "?gid=" + gid + "&page=" + page,
				page, true);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				JSONArray jsonArray = new JSONArray(String.valueOf(response
						.get("list")));
				if (response.getInt("resultCode") == RESULTCODE_SUCCESS) {
					list = new ArrayList<Article>();
					int jsonLength = jsonArray.length();
					Article article = null;
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						article = new Article(obj);
						list.add(article);
					}
				}
				map.put("list", list);
				map.put("pageTotal", response.get("obj"));
			} catch (JSONException e) {
				result = Constants.RESULTCODE_FAIL;
				e.printStackTrace();
			}
		} else {
			result = Constants.RESULTCODE_FAIL;
		}
		map.put("result", result);
		return map;
	}
}
