package com.ulewo.util;

import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.bean.RequestResult;
import com.ulewo.enums.ResultEnum;

public class JsonUtil {
	public static Object[] converJson2Objects(String jsonString, Class c) {
		RequestResult requestResult = null;
		if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
			JSONObject jsonObj = requestResult.getJsonObject();
			JSONArray jsonArray = null;
			try {
				jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
			} catch (JSONException e) {

				e.printStackTrace();
			}
			int jsonLength = jsonArray.length();
			Field[] fields = c.getDeclaredFields();// 拿到数据成员
			for (Field f : fields) {
				System.out.println("该类的内部变量有:" + f.getName());
			}
			for (int i = 0; i < jsonLength; i++) {

			}
		}
		return null;
	}
}
