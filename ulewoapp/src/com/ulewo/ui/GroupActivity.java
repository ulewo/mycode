package com.ulewo.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
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
import com.ulewo.adapter.GroupListAdapter;
import com.ulewo.bean.Group;
import com.ulewo.bean.Task;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;

public class GroupActivity extends BaseActivity implements IMainActivity {

	private LinearLayout progressBar = null;

	private GroupListAdapter adapter = null;

	private View loadMoreView = null;

	private int page = 1;

	private TextView loadmoreTextView = null;

	private LinearLayout loadmore_prgressbar = null;

	ListView listView = null;

	private ImageButton refreshBtn = null;

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	private String gid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);
		ExitApplication.getInstance().addActivity(this);
		init();
		Intent service = new Intent(this, MainService.class);
		startService(service);

		HashMap<String, Object> param = new HashMap<String, Object>(1);
		param.put("page", page);
		Task task = new Task(TaskType.GROUP, param, this);
		MainService.newTask(task);
	}

	private void init() {

		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.wowo);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_wowo);

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
				Intent service = new Intent(GroupActivity.this, MainService.class);
				startService(service);
				HashMap<String, Object> param = new HashMap<String, Object>(1);
				param.put("page", ++page);
				Task task = new Task(TaskType.QUERYARTICLES, param, GroupActivity.this);
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
				Task task = new Task(TaskType.QUERYARTICLES, param, GroupActivity.this);
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
			ArrayList<Group> list = (ArrayList<Group>) myobj.get("list");
			if (adapter == null || page == 1) {
				adapter = new GroupListAdapter(this, list, new AsyncImageLoader(), listView);
				listView.setAdapter(adapter);
			}
			else {
				loadmore_prgressbar.setVisibility(View.GONE);
				loadmoreTextView.setVisibility(View.VISIBLE);
				adapter.loadMore(list);
			}
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {

					Group group = (Group) adapter.getItem(postion);
					String gid = group.getGid();
					String groupIcon = group.getGroupIcon();
					String gName = group.getgName();
					String gUserName = group.getgUserName();
					String gMember = group.getgMember();
					String gArticleCount = group.getgArticleCount();
					if (!"0".equals(gid)) {
						Intent intent = new Intent();
						intent.putExtra("gid", gid);
						intent.putExtra("groupIcon", groupIcon);
						intent.putExtra("gName", gName);
						intent.putExtra("gUserName", gUserName);
						intent.putExtra("gMember", gMember);
						intent.putExtra("gArticleCount", gArticleCount);
						intent.setClass(GroupActivity.this, ShowGroupActivity.class);
						startActivity(intent);
					}
				}
			});
		}
		else {
			Toast.makeText(GroupActivity.this, R.string.request_timeout, Toast.LENGTH_LONG).show();
			progressBar.setVisibility(View.GONE);
			loadmoreTextView.setVisibility(View.VISIBLE);
		}

	}
}
