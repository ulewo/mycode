package com.ulewo.cache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;

import com.ulewo.util.StringUtils;

public class AsyncImageLoader {

	private static double MB = 1024 * 1024;

	private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;

	private static final String SEPARATOR = File.separator;

	private static final String ULEWO = "ulewo";

	private HashMap<String, SoftReference<Drawable>> imageCache;

	public AsyncImageLoader() {

		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback imageCallback, boolean readCache) {
		final String fileName = StringUtils.convertUrlToFileName(imageUrl);
		// 从缓存中取
		if (readCache) {
			// 从内存中读取
			if (imageCache.containsKey(fileName)) {
				SoftReference<Drawable> softReference = imageCache
						.get(fileName);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					return drawable;
				}
			}
			// 从sdk中读取
			Drawable drawable = readFromSDK(fileName);
			if (null != drawable) {
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

				// 从网络上下载图片
				Drawable drawable = loadImageFromUrl(imageUrl);
				// 将图片保存到sd卡
				save2SDK(drawable, fileName);
				if (null != drawable) {
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
				}
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	public Drawable loadImageFromUrlNoSynchronization(String imageUrl) {
		try {
			final String fileName = StringUtils.convertUrlToFileName(imageUrl);
			// 从缓存中取
			if (imageCache.containsKey(fileName)) {
				SoftReference<Drawable> softReference = imageCache
						.get(fileName);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					return drawable;
				}
			}
			// 从sdk中读取
			Drawable drawable = readFromSDK(fileName);
			if (null != drawable) {
				return drawable;
			}

			// 从网络上下载图片
			drawable = loadImageFromUrl(imageUrl);
			// 将图片保存到sd卡
			save2SDK(drawable, fileName);
			imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
			return drawable;
		} catch (Exception e) {

		}
		return null;
	}

	public static Drawable loadImageFromUrl(String url) {

		URL m;
		InputStream i = null;
		Drawable d = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			d = Drawable.createFromStream(i, "src");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (i != null) {
				try {
					i.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

	// 保存到sdk
	private void save2SDK(Drawable drawable, String imageUrl) {

		if (drawable == null) {
			return;
		}
		Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
		if (bm == null) {
			return;
		}
		// 判断sdcard上的空间 ,如果剩余空间小于1M，那么就不缓存了。
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			return;
		}
		String filename = StringUtils.convertUrlToFileName(imageUrl);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			FileOutputStream fos = null;
			try {
				String sDir = Environment.getExternalStorageDirectory()
						+ SEPARATOR + ULEWO;
				File destDir = new File(sDir);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				File file = new File(sDir, filename);
				fos = new FileOutputStream(file);
				bm.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != fos) {
						fos.close();
					}
				} catch (Exception e) {
				}
			}
		}
	}

	// 计算sdcard上的剩余空间
	private int freeSpaceOnSd() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
					.getBlockSize()) / MB;
			return (int) sdFreeMB;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * description: 修改文件的最后修改时间
	 * 
	 * @param dir
	 * @param fileName
	 * @author luohl
	 */
	private void updateFileTime(String dir, String fileName) {

		File file = new File(dir, fileName);
		if (file.exists()) {
			long newModifiedTime = System.currentTimeMillis();
			file.setLastModified(newModifiedTime);
		}
	}

	private Drawable readFromSDK(String filename) {

		Bitmap btp = null;
		Drawable drawable = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			File file = new File(Environment.getExternalStorageDirectory()
					+ SEPARATOR + ULEWO, filename);
			if (file.exists()) {
				FileInputStream fs = null;
				BufferedInputStream bs = null;

				btp = null;
				try {
					fs = new FileInputStream(file);
					bs = new BufferedInputStream(fs);
					btp = BitmapFactory.decodeStream(bs);
					if (btp == null) {
						return null;
					}
					drawable = new BitmapDrawable(btp);
				} catch (Exception e) {

				} finally {
					try {
						if (null != fs) {
							fs.close();
						}

					} catch (Exception e) {
					}
					try {
						if (null != bs) {
							bs.close();
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return drawable;
	}
}
