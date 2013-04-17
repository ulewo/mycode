package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ArticleList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<Article> articleList = new ArrayList<Article>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<Article> getArticleList() {

		return articleList;
	}

	public void setArticleList(ArrayList<Article> articleList) {

		this.articleList = articleList;
	}

	public static ArticleList parse(JSONObject jsonObj) throws Exception {

		ArticleList list = new ArticleList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getArticleList().add(Article.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		}
		catch (Exception e) {
			throw e;
		}
		return list;
	}
}
