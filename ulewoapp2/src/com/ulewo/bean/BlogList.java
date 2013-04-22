package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class BlogList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<Blog> blogList = new ArrayList<Blog>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<Blog> getBlogList() {

		return blogList;
	}

	public void setBlogList(ArrayList<Blog> blogList) {

		this.blogList = blogList;
	}

	public static BlogList parse(JSONObject jsonObj) throws AppException {

		BlogList list = new BlogList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getBlogList().add(Blog.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
		return list;
	}
}
