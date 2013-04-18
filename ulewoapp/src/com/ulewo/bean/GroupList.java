package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class GroupList implements Serializable {
	private static final long serialVersionUID = -6956425724309545864L;

	private int pageTotal;

	private ArrayList<Group> groupList = new ArrayList<Group>();

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public ArrayList<Group> getGroupList() {

		return groupList;
	}

	public void setGroupList(ArrayList<Group> groupList) {

		this.groupList = groupList;
	}

	public static GroupList parse(JSONObject jsonObj) throws AppException {

		GroupList list = new GroupList();
		try {
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				list.getGroupList().add(Group.parse(obj));
			}
			list.setPageTotal(jsonObj.getInt("pageTotal"));
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
		return list;
	}
}
