package com.ulewo.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;
import com.ulewo.adapter.ArticleListAdapter;
import com.ulewo.bean.Article;
import com.ulewo.bean.Task;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;

public class ShowGroupActivity extends BaseActivity implements IMainActivity {

	private LinearLayout progressBar = null;

	private ArticleListAdapter adapter = null;

	private View loadMoreView = null;

	private TextView loadmoreTextView = null;

	private LinearLayout loadmore_prgressbar = null;

	ListView listView = null;

	private ImageButton refreshBtn = null;

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	private String gid = "";

	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.group);
		ExitApplication.getInstance().addActivity(this);
		init();

		Intent service = new Intent(this, MainService.class);
		startService(service);
		HashMap<String, Object> param = new HashMap<String, Object>(1);
		param.put("gid", this.gid);
		param.put("page", this.page);
		Task task = new Task(TaskType.SHOWGROUP, param, this);
		MainService.newTask(task);
	}

	private void init() {

		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.wowo);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_wowo);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		this.gid = bunde.getString("gid");
		String groupIcon = bunde.getString("groupIcon");
		String gName = bunde.getString("gName");
		String gUserName = bunde.getString("gUserName");
		String gMember = bunde.getString("gMember");
		String gArticleCount = bunde.getString("gArticleCount");

		final ImageView group_icon = (ImageView) findViewById(R.id.wowo_icon);
		TextView titView = (TextView) findViewById(R.id.wowo_tit);
		TextView authorView = (TextView) findViewById(R.id.wowo_username_con);
		TextView memberView = (TextView) findViewById(R.id.wowo_member_con);
		TextView articleView = (TextView) findViewById(R.id.wowo_articlecount_con);

		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedImage = asyncImageLoader.loadDrawable(groupIcon, new ImageCallback() {
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {

				group_icon.setImageDrawable(imageDrawable);
			}
		});
		if (cachedImage == null) {
			group_icon.setImageResource(R.drawable.icon);
		}
		else {
			group_icon.setImageDrawable(cachedImage);
		}
		// imageView.setImageBitmap(returnBitMap(blog.getGroupIcon()));
		titView.setText(gName);
		authorView.setText(gUserName);
		memberView.setText(gMember);
		articleView.setText(gArticleCount);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);

		loadMoreView = View.inflate(this, R.layout.loadmore, null);

		listView = (ListView) findViewById(R.id.article_list_view_id);
		listView.addFooterView(loadMoreView);

		loadmore_prgressbar = (LinearLayout) findViewById(R.id.loadmore_progressbar);
		loadmoreTextView = (TextView) findViewById(R.id.loadmoretextview);

		loadmoreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				loadmoreTextView.setVisibility(View.GONE);
				loadmore_prgressbar.setVisibility(View.VISIBLE);
				Intent service = new Intent(ShowGroupActivity.this, MainService.class);
				startService(service);
				HashMap<String, Object> param = new HashMap<String, Object>(1);
				param.put("page", ++page);
				param.put("gid", gid);
				Task task = new Task(TaskType.SHOWGROUP, param, ShowGroupActivity.this);
				MainService.newTask(task);
			}
		});

		refreshBtn = (ImageButton) findViewById(R.id.head_refresh);
		refreshBtn.setVisibility(View.VISIBLE);
		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				progressBar.setVisibility(View.VISIBLE);
				page = 1;
				HashMap<String, Object> param = new HashMap<String, Object>(1);
				param.put("page", page);
				param.put("gid", gid);
				Task task = new Task(TaskType.SHOWGROUP, param, ShowGroupActivity.this);
				MainService.newTask(task);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {

		progressBar.setVisibility(View.GONE);
		refreshBtn.clearAnimation();
		HashMap<String, Object> myobj = (HashMap<String, Object>) obj[0];
		if (Constants.RESULTCODE_SUCCESS.equals(myobj.get("resultCode").toString())) {
			ArrayList<Article> list = (ArrayList<Article>) myobj.get("list");
			if (adapter == null || page == 1) {
				adapter = new ArticleListAdapter(this, list);
				listView.setAdapter(adapter);
			}
			else {
				loadmore_prgressbar.setVisibility(View.GONE);
				loadmoreTextView.setVisibility(View.VISIBLE);
				adapter.loadMore(list);
			}
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {

					String articleId = String.valueOf(adapter.getItemId(postion));
					if (!"0".equals(articleId)) {
						Intent intent = new Intent();
						intent.putExtra("articleId", articleId);
						intent.setClass(ShowGroupActivity.this, ShowArticleActivity.class);
						startActivity(intent);
					}
				}
			});
		}
		else {
			Toast.makeText(ShowGroupActivity.this, R.string.request_timeout, Toast.LENGTH_LONG).show();
			progressBar.setVisibility(View.GONE);
			loadmoreTextView.setVisibility(View.VISIBLE);
		}

	}

	private Bitmap returnBitMap(String url) {

		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
