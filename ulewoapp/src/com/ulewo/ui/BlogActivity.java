package com.ulewo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.ulewo.R;

public class BlogActivity extends BaseActivity {

	private Button tagBtn01;
	private Button tagBtn02;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog);
		LinearLayout loading = (LinearLayout)findViewById(R.id.loading);
		loading.getBackground().setAlpha(30);
		loading.setOnClickListener(null);
		
		tagBtn01 = (Button)findViewById(R.id.blog_tag_btn01);
		tagBtn02 = (Button)findViewById(R.id.blog_tag_btn02);
		tagBtn01.setBackgroundResource(R.drawable.border_button);
		tagBtn02.setBackgroundResource(R.drawable.border_button2);
		tagBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tagBtn01.setBackgroundResource(R.drawable.border_button);
				tagBtn02.setBackgroundResource(R.drawable.border_button2);
			}
		});
		tagBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tagBtn01.setBackgroundResource(R.drawable.border_button2);
				tagBtn02.setBackgroundResource(R.drawable.border_button);
			}
		});
	}
}
