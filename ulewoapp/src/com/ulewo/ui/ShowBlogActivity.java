package com.ulewo.ui;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.Enum.ResultEnum;
import com.ulewo.api.ApiClient;
import com.ulewo.bean.RequestResult;
import com.ulewo.handler.MxgsaTagHandler;

public class ShowBlogActivity extends Activity {

	private static final String path = "http://192.168.0.224:8080/ulewo/android/showBlog.jspx";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.blog);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_blog);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		String articleId = bunde.getString("articleId");

		try {
			RequestResult requestResult = ApiClient.getUlewoInfo(path + "?articleId=" + articleId);
			if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
				JSONObject jsonObj = requestResult.getJsonObject();
				JSONObject myobj = new JSONObject(jsonObj.getString("article"));
				TextView showView = (TextView) findViewById(R.id.show_article_id);
				TextView titleView = (TextView) findViewById(R.id.show_article_title);
				TextView authorView = (TextView) findViewById(R.id.article_author);
				TextView timeView = (TextView) findViewById(R.id.article_time);
				TextView recountView = (TextView) findViewById(R.id.article_recount);
				titleView.setText(myobj.getString("title"));
				authorView.setText(myobj.getString("userName"));
				timeView.setText(String.valueOf(myobj.getString("postTime")).substring(0, 16));
				recountView.setText(myobj.getString("reCount"));
				showView.setText(Html.fromHtml(myobj.getString("content"), null, new MxgsaTagHandler(this)));
				showView.setClickable(true);
				showView.setMovementMethod(LinkMovementMethod.getInstance());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
