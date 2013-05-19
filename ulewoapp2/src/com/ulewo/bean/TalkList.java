package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class TalkList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<Talk> talkList = new ArrayList<Talk>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<Talk> getTalkList() {

		return talkList;
	}

	public void setArticleList(ArrayList<Talk> articleList) {

		this.talkList = articleList;
	}

	public static TalkList parse(JSONObject jsonObj) throws AppException {
		TalkList list = new TalkList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj
					.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getTalkList().add(Talk.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
		return list;
	}
}
