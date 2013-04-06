package com.ulewo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.api.ApiClient;
import com.ulewo.bean.Article;
import com.ulewo.bean.RequestResult;
import com.ulewo.enums.ResultEnum;

public class Ulewo {
	private static final String BASEURL_ARTICLELIST = "http://ulewo.cloudfoundry.com/android/fetchArticle.jspx";

	private static final String BASEUR_SHOWARTICLE = "http://192.168.2.224:8080/ulewo/android/showArticle.jspx";

	private static final String BASEUR_BLOGLIST = "http://192.168.2.224:8080/ulewo/android/showBlog.jspx";

	private static final String BASEUR_SHOWBLOG = "http://192.168.2.224:8080/ulewo/android/showBlog.jspx";

	public static List<Article> queryArticleList(int page) {
		List<Article> list = new ArrayList<Article>();
		RequestResult requestResult = ApiClient
				.getUlewoInfo(BASEURL_ARTICLELIST + "?page=" + page);
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			JSONArray jsonArray = null;
			try {
				jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
				int jsonLength = jsonArray.length();
				Article article = null;
				for (int i = 0; i < jsonLength; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					article = new Article(obj);
					list.add(article);
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
				jsonObj = new JSONObject(String.valueOf(jsonObj.get("article")));
				article = new Article(jsonObj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return article;
	}
}
