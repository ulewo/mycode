package com.ulewo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ulewo.R;

public class WoWoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.wowo);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.wowo);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_wowo);
	}
}
