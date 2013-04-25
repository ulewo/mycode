package com.ulewo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.R;
import com.ulewo.common.UIHelper;

public class AboutActivity extends BaseActivity {

	TextView more_about_view = null;
	AppContext appContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.about);
		appContext = (AppContext) getApplication();
		
		Button backBtn = null;
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(UIHelper.finish(this));
		more_about_view = (TextView) findViewById(R.id.more_about_view);
		more_about_view.setText("有乐窝");
		
	}
}
