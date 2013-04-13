package com.ulewo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ulewo.R;

public class MoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.more);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_more);
	}

}
