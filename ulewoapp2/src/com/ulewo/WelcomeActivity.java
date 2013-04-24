package com.ulewo;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.ulewo.bean.LoginUser;
import com.ulewo.bean.User;
import com.ulewo.ui.BaseActivity;
import com.ulewo.ui.MainActivity;
import com.ulewo.util.StringUtils;

public class WelcomeActivity extends BaseActivity {

	private ImageView imageView = null;

	private final static long time = 3000;

	private AppContext appContext = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		imageView = (ImageView) findViewById(R.id.wlecomelogo);
		appContext = (AppContext) getApplication();
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

				String key = StringUtils.encodeByMD5("user");
				if (appContext.isReadDataCache(key)) {
					LoginUser loginUser = (LoginUser) appContext
							.readObject(key);
					if (loginUser != null) {
						User user = loginUser.getUser();
						try {
							appContext.login(user.getUserName(),
									user.getPassword(), true);
						} catch (AppException e) {
						}
					}
				}

			}
		}.start();
	}
}