package com.ulewo.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.R;

public class AboutActivity extends BaseActivity {

	TextView more_about_view = null;
	AppContext appContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.about);
		appContext = (AppContext) getApplication();
		more_about_view = (TextView) findViewById(R.id.more_about_view);
	}
}
