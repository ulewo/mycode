package com.ulewo.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.ulewo.R;

public class MainActivity extends TabActivity {
	private static final String TAG_ARTICLE = "article";

	private static final String TAG_BLOG = "blog";

	private static final String TAG_WOWO = "wowo";

	private static final String TAG_USER = "user";

	private static final String TAG_MORE = "more";

	TabHost tabHost = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec(TAG_ARTICLE).setIndicator(TAG_ARTICLE)
				.setContent(new Intent(this, ArticleActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAG_BLOG).setIndicator(TAG_BLOG)
				.setContent(new Intent(this, BlogActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAG_WOWO).setIndicator(TAG_WOWO)
				.setContent(new Intent(this, GroupActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAG_USER).setIndicator(TAG_USER)
				.setContent(new Intent(this, UserActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAG_MORE).setIndicator(TAG_MORE)
				.setContent(new Intent(this, MoreActivity.class)));

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group_id);
		radioGroup.check(R.id.radio_article_id);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.radio_article_id:
					tabHost.setCurrentTabByTag(TAG_ARTICLE);
					break;
				case R.id.radio_blog_id:
					tabHost.setCurrentTabByTag(TAG_BLOG);
					break;
				case R.id.radio_wowo_id:
					tabHost.setCurrentTabByTag(TAG_WOWO);
					break;
				case R.id.radio_user_id:
					tabHost.setCurrentTabByTag(TAG_USER);
					break;
				case R.id.radio_more_id:
					tabHost.setCurrentTabByTag(TAG_MORE);
					break;
				}
			}
		});
	}
}