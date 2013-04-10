package com.ulewo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.ulewo.ui.ExitApplication;
import com.ulewo.ui.MainActivity;

public class WelcomeActivity extends Activity {

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

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			// 效果结束后，跳转到新的activity
			public void onAnimationEnd(Animation animation) {

				Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(mainIntent);

			}
		});
		imageView.setAnimation(alphaAnimation);
	}
}