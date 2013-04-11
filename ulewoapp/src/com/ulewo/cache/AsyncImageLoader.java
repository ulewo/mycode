package com.ulewo.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;

import com.ulewo.util.Tools;

public class AsyncImageLoader {

	private static double MB = 1024 * 1024;

	private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;

	private HashMap<String, SoftReference<Drawable>> imageCache;

	public AsyncImageLoader() {

		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {

		//从内存中读取
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {

				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {

				//从网络上下载图片
				Drawable drawable = loadImageFromUrl(imageUrl);
				//将图片保存到sd卡
				save2SDK(drawable, imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromUrl(String url) {

		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		}
		catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

	//保存到sdk
	private void save2SDK(Drawable drawable, String imageUrl) {

		Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
		if (bm == null) {
			return;
		}
		//判断sdcard上的空间 ,如果剩余空间小于1M，那么就不缓存了。
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			return;
		}
		String filename = Tools.convertUrlToFileName(imageUrl);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			FileOutputStream fos = null;
			try {
				File file = new File(Environment.getExternalStorageDirectory(), filename);
				fos = new FileOutputStream(file);
				bm.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (null != fos) {
						fos.close();
					}
				}
				catch (Exception e) {
				}
			}
		}
	}

	//计算sdcard上的剩余空间
	private int freeSpaceOnSd() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
			double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
			return (int) sdFreeMB;
		}
		else {
			return 0;
		}
	}

	/**
	 * 
	 * description: 修改文件的最后修改时间 
	 * @param dir
	 * @param fileName
	 * @author luohl
	 */
	private void updateFileTime(String dir, String fileName) {

		File file = new File(dir, fileName);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}
}
