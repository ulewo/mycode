package com.ulewo.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LazyImageLoader {

	private static final int MESSAGE_ID = 1;

	public static final String EXTAR_IMG_URL = "extra_img_url";

	public static final String EXTAR_IMG = "extra_img";

	private static ImageManger imgManger;

	private CallbackManager callbackManager = new CallbackManager();

	private static BlockingQueue<String> urlQueue = new ArrayBlockingQueue<String>(
			50);

	public Bitmap get(String url, ImageLoaderCallback callback) {
		Bitmap bitmap = null;
		if (imgManger.contians(url)) {
			bitmap = imgManger.getFromCache(url);
			return bitmap;

		} else {
			putUrl2Queue(url);
			startDownLoadThread();
		}
		return null;
	}

	private void putUrl2Queue(String url) {
		if (!urlQueue.contains(url)) {
			try {
				urlQueue.put(url);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void startDownLoadThread() {

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MESSAGE_ID:
				Bundle bundle = msg.getData();
				String url = bundle.getString(EXTAR_IMG_URL);
				Bitmap bitmap = bundle.getParcelable(EXTAR_IMG);
				callbackManager.callback(url, bitmap);
				break;

			default:
				break;
			}
		}
	};

	private class DownLoadImageThread implements Runnable {
		private boolean isRun = true;

		@Override
		public void run() {
			try {
				while (isRun) {
					String url = urlQueue.poll();
					if (null == url) {
						break;
					}
					Bitmap bitmap = imgManger.safeGet(url);
					Message msg = handler.obtainMessage(MESSAGE_ID);
					Bundle bundle = msg.getData();
					bundle.putSerializable(EXTAR_IMG_URL, url);
					bundle.putParcelable(EXTAR_IMG, bitmap);
					handler.sendMessage(msg);
				}
			} catch (Exception e) {

			}
		}
	}
}
