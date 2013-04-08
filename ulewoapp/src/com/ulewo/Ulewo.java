package com.ulewo;

import java.util.ArrayList;
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

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	public static List<Article> queryArticleList(int page) {

		List<Article> list = null;
		RequestResult requestResult = ApiClient
				.getUlewoInfo(BASEURL_ARTICLELIST + "?page=" + page);
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

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		} else {
			return null;
		}
	}

	public static Article queryArticle(int articleId) {

		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_SHOWARTICLE
				+ "?articleId=" + articleId);
		Article article = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				article = new Article(new JSONObject(response.get("obj")
						.toString()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return article;
	}

	public static List<Blog> queryBlogList(int page) {

		List<Blog> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_BLOGLIST
				+ "?page=" + page);
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

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		} else {
			return null;
		}
	}

	public static Blog queryBlog(int articleId) {

		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_SHOWBLOG
				+ "?articleId=" + articleId);
		Blog blog = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			try {
				JSONObject response = new JSONObject(String.valueOf(jsonObj
						.get("response")));
				blog = new Blog(new JSONObject(response.get("obj").toString()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return blog;
	}

	public static List<Group> queryGroupList(int page) {

		List<Group> list = null;
		RequestResult requestResult = ApiClient.getUlewoInfo(BASEUR_GROUPLIST
				+ "?page=" + page);
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

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		} else {
			return null;
		}
	}

	public static List<Article> queryArticleListByGid(String gid, int page) {

		List<Article> list = null;
		RequestResult requestResult = ApiClient
				.getUlewoInfo(BASEUR_GROUPARTICLELIST + "?page=" + page
						+ "&gid=" + gid);
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		} else {
			return null;
		}
	}
}
