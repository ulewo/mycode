package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class ReTalkList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<ReTalk> retalkList = new ArrayList<ReTalk>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<ReTalk> getReTalkList() {

		return retalkList;
	}

	public void setArticleList(ArrayList<ReTalk> retalkList) {

		this.retalkList = retalkList;
	}

	public static ReTalkList parse(JSONObject jsonObj) throws AppException {
		ReTalkList list = new ReTalkList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj
					.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getReTalkList().add(ReTalk.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
		return list;
	}
}
