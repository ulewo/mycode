package com.ulewo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.ulewo.ui.MainActivity;

public class WelcomeActivity extends Activity {
	private ImageView imageView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		imageView = (ImageView) findViewById(R.id.wlecomelogo);
		AnimationSet animationset = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2500);
		animationset.addAnimation(alphaAnimation);
		imageView.startAnimation(animationset);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		}, 2500);
	}
}