package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class ReBlogList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<ReBlog> reBlogList = new ArrayList<ReBlog>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<ReBlog> getReBlogList() {
		return reBlogList;
	}

	public void setReBlogList(ArrayList<ReBlog> reBlogList) {
		this.reBlogList = reBlogList;
	}

	public static ReBlogList parse(JSONObject jsonObj) throws AppException {

		ReBlogList list = new ReBlogList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj
					.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getReBlogList().add(ReBlog.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
		return list;
	}
}
