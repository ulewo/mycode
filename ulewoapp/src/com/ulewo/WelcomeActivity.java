package com.ulewo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.ulewo.ui.BaseActivity;
import com.ulewo.ui.ExitApplication;
import com.ulewo.ui.MainActivity;
import com.ulewo.util.Constants;

public class WelcomeActivity extends BaseActivity {

	private ImageView imageView = null;

	private final static long time = 3000;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		ExitApplication.getInstance().addActivity(this);
		imageView = (ImageView) findViewById(R.id.wlecomelogo);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(time);

		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				initLogin();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			// 效果结束后，跳转到新的activity
			public void onAnimationEnd(Animation animation) {

				Intent mainIntent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
			}
		});
		imageView.setAnimation(alphaAnimation);
	}

	private void initLogin() {
		new Thread() {
			public void run() {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					BufferedReader read = null;
					InputStreamReader fos = null;
					FileInputStream filein = null;
					StringBuffer sb = new StringBuffer();
					try {
						String sDir = Environment.getExternalStorageDirectory()
								+ Constants.SEPARATOR + Constants.ULEWO;
						File file = new File(sDir, Constants.USERACCOUNT);
						if (!file.exists()) {
							return;
						}
						filein = new FileInputStream(file);
						fos = new InputStreamReader(filein);
						read = new BufferedReader(fos);
						String str = null;
						while ((str = read.readLine()) != null) {
							sb.append(str);
						}
						String strSb = String.valueOf(sb);
						String strSbs[] = strSb.split(";");
						if (strSbs.length > 1) {
							String userName = strSbs[0];
							String password = strSbs[1];
							Ulewo.login(userName, password);
						}

					} catch (Exception e) {
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
			}
		}.start();
	}
}