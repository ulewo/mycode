package com.ulewo.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ulewo.util.StringUtils;

public class ImageManger {
	Map<String, SoftReference<Bitmap>> imgCache = null;

	private Context context;

	public ImageManger(Context context) {

		this.context = context;
		imgCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public boolean contians(String url) {

		return imgCache.containsKey(url);
	}

	public Bitmap getFromCache(String url) {

		Bitmap bitmap = null;
		bitmap = this.getFromCache(url);
		if (null == bitmap) {
			bitmap = getFromMapCashe(url);
		}
		else {
			bitmap = getFromFile(url);
		}
		return bitmap;
	}

	/**
	 * �ӻ�����ȡ
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getFromMapCashe(String url) {

		Bitmap bitmap = null;
		SoftReference<Bitmap> ref = null;
		synchronized (this) {
			ref = imgCache.get(url);
		}
		if (null != ref) {
			bitmap = ref.get();
		}
		return bitmap;
	}

	/**
	 * 从文件中获取
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getFromFile(String url) {

		String fileName = StringUtils.encodeByMD5(url);
		FileInputStream in = null;
		try {
			in = context.openFileInput(fileName);
			return BitmapFactory.decodeStream(in);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (null != in) {
				try {
					in.close();
				}
				catch (Exception e) {

				}
			}
		}
	}

	public Bitmap downloadImg(String urlstr) {

		InputStream in = null;
		try {
			URL url = new URL(urlstr);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			in = connection.getInputStream();
			String fileName = writer2File(urlstr, in);
			return BitmapFactory.decodeFile(fileName);
			/*
			 * return Drawable.createFromStream(connection.getInputStream(),
			 * "image");
			 */
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap safeGet(String url) throws Exception {

		Bitmap bitmap = this.getFromFile(url);
		if (null != bitmap) {
			synchronized (this) {
				imgCache.put(url, new SoftReference<Bitmap>(bitmap));
			}
			return bitmap;
		}
		return downloadImg(url);
	}

	public String writer2File(String fileName, InputStream in) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}
			bos.flush();
		}
		catch (Exception e) {

		}
		finally {
			if (null != bis) {
				try {
					bis.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bos) {
				try {
					bos.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return context.getFilesDir() + "/" + fileName;
	}
}
