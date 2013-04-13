package com.ulewo.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import android.os.Environment;

import com.ulewo.bean.RequestResult;
import com.ulewo.enums.ResultEnum;
import com.ulewo.util.Tools;

public class ApiClient {
	private static final int HTTP_200 = 200;

	private static final String SUCCESS = "success";

	private static final String REQUESTTIMEOUT = "requesttimeout";

	private static final String SEPARATOR = File.separator;

	private static final String ULEWO = "ulewo";

	public static RequestResult getUlewoInfo(String path, int page,
			boolean isCache) {

		RequestResult requestResult = new RequestResult();
		String fileName = Tools.encodeByMD5(path.substring(0,
				path.lastIndexOf("?")));

		// 如果page==0读取缓存
		if (page == 0 && isCache) {
			requestResult = readFromSdk(fileName);
			if (requestResult != null) {
				return requestResult;
			}

		}
		if (null == requestResult) {
			requestResult = new RequestResult();
		}
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
				String myString = EncodingUtils.getString(baf.toByteArray(),
						"UTF-8");
				// 保存缓存到sdk
				if ((page == 1 || page == 0) && isCache) {
					SaveDateThread thread = new SaveDateThread(fileName,
							baf.toByteArray());
					Thread mythread = new Thread(thread);
					mythread.start();
				}
				JSONObject jsonObj = new JSONObject(myString);
				requestResult.setResultEnum(ResultEnum.SUCCESS);
				requestResult.setJsonObject(jsonObj);
			} else {
				requestResult.setResultEnum(ResultEnum.REQUESTTIMEOUT);
			}
			// 关闭连接
			urlConn.disconnect();

		} catch (Exception e) {
			requestResult.setResultEnum(ResultEnum.ERROR);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					bis = null;
				}
				bis = null;
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
				}
				bis = null;
			}
		}
		return requestResult;
	}

	static class SaveDateThread implements Runnable {
		private String url;

		private byte[] data;

		public SaveDateThread(String url, byte[] data) {

			this.url = url;
			this.data = data;
		}

		@Override
		public void run() {

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				OutputStream out = null;
				try {
					String sDir = Environment.getExternalStorageDirectory()
							+ SEPARATOR + ULEWO;
					File destDir = new File(sDir);
					if (!destDir.exists()) {
						destDir.mkdirs();
					}
					File file = new File(sDir, url);
					if (!file.exists()) {
						file.createNewFile();
					}
					out = new FileOutputStream(file);
					out.write(data);
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != out) {
							out.close();
						}
					} catch (Exception e) {
					}
				}
			}

		}
	}

	private static RequestResult readFromSdk(String fileName) {

		RequestResult requestResult = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			BufferedReader read = null;
			InputStreamReader fos = null;
			FileInputStream filein = null;
			StringBuffer sb = new StringBuffer();
			try {
				String sDir = Environment.getExternalStorageDirectory()
						+ SEPARATOR + ULEWO;
				File file = new File(sDir, fileName);
				if (!file.exists()) {
					return null;
				}
				filein = new FileInputStream(file);
				fos = new InputStreamReader(filein);
				read = new BufferedReader(fos);
				String str = null;
				while ((str = read.readLine()) != null) {
					sb.append(str);
				}
				JSONObject jsonObj = new JSONObject(String.valueOf(sb));
				requestResult = new RequestResult();
				requestResult.setResultEnum(ResultEnum.SUCCESS);
				requestResult.setJsonObject(jsonObj);
			} catch (Exception e) {
				requestResult = null;
				e.printStackTrace();
			} finally {
				try {
					if (null != filein) {
						filein.close();
					}
				} catch (Exception e) {
				}
				try {
					if (null != fos) {
						fos.close();
					}
				} catch (Exception e) {
				}
				try {
					if (null != read) {
						read.close();
					}
				} catch (Exception e) {
				}
			}
		}
		return requestResult;
	}
}
