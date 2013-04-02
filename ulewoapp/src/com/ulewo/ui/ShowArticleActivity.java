package com.ulewo.ui;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.handler.MxgsaTagHandler;

public class ShowArticleActivity extends Activity {
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.article);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		String articleId = bunde.getString("articleId");

		String myString = "";
		try {
			// 定义获取文件内容的URL
			URL myURL = new URL(
					"http://192.168.2.224:8080/ulewo/android/showArticle.jspx?articleId="
							+ articleId);
			// 打开URL链接
			URLConnection ucon = myURL.openConnection();
			// 使用InputStream，从URLConnection读取数据
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			// 用ByteArrayBuffer缓存
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
			JSONObject jsonObj = new JSONObject(myString);
			JSONObject myobj = new JSONObject(jsonObj.getString("article"));
			TextView showView = (TextView) findViewById(R.id.show_article_id);
			TextView titleView = (TextView) findViewById(R.id.show_article_title);
			titleView.setText(myobj.getString("title"));
			showView.setText(Html.fromHtml(myobj.getString("content"), null,
					new MxgsaTagHandler(this)));
			showView.setClickable(true);
			showView.setMovementMethod(LinkMovementMethod.getInstance());

		} catch (Exception e) {
			e.printStackTrace();
			myString = e.getMessage();
		}
	}
}
