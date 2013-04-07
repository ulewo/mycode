package com.ulewo.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import com.ulewo.bean.RequestResult;
import com.ulewo.enums.ResultEnum;

public class ApiClient {
	private static final int HTTP_200 = 200;

	private static final String SUCCESS = "success";

	private static final String REQUESTTIMEOUT = "requesttimeout";

	public static RequestResult getUlewoInfo(String path) {

		RequestResult requestResult = new RequestResult();
		InputStream is = null;
		HttpURLConnection urlConn = null;
		BufferedInputStream bis = null;
		try {
			// 新建一个URL对象
			URL url = new URL(path);
			// 打开一个HttpURLConnection连接
			urlConn = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间
			urlConn.setConnectTimeout(5 * 1000);
			// 开始连接
			urlConn.connect();
			// 判断请求是否成功
			if (urlConn.getResponseCode() == HTTP_200) {
				// 获取返回的数据
				is = urlConn.getInputStream();
				bis = new BufferedInputStream(is);
				// 用ByteArrayBuffer缓存
				ByteArrayBuffer baf = new ByteArrayBuffer(50);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				String myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
				JSONObject jsonObj = new JSONObject(myString);
				requestResult.setResultEnum(ResultEnum.SUCCESS);
				requestResult.setJsonObject(jsonObj);
			}
			else {
				requestResult.setResultEnum(ResultEnum.REQUESTTIMEOUT);
			}
			// 关闭连接
			urlConn.disconnect();

		}
		catch (Exception e) {
			requestResult.setResultEnum(ResultEnum.ERROR);
		}
		finally {
			if (bis != null) {
				try {
					bis.close();
				}
				catch (IOException e) {
					bis = null;
				}
				bis = null;
			}
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException e) {
					is = null;
				}
				bis = null;
			}
		}
		return requestResult;
	}
}
