package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class ReArticleList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<ReArticle> reArticleList = new ArrayList<ReArticle>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<ReArticle> getReArticleList() {

		return reArticleList;
	}

	public void setReArticleList(ArrayList<ReArticle> reArticleList) {

		this.reArticleList = reArticleList;
	}

	public static ReArticleList parse(JSONObject jsonObj) throws AppException {

		ReArticleList list = new ReArticleList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getReArticleList().add(ReArticle.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
		return list;
	}
}
