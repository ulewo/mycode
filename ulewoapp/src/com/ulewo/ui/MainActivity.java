package com.ulewo.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.ulewo.R;

public class MainActivity extends BaseActivity {
	private String groupName[] = null;
	private int topicCount[] = null;
	private int images[] = null;
	private Button signinBtn;
	private Button blogBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		groupName = new String[] { "java自学窝", "我爱IT资讯", "网上那点事", "跟我一起学CSS",
				"跟我一起学CSS", "跟我一起学CSS", "跟我一起学CSS", "跟我一起学CSS" };
		topicCount = new int[] { 200, 100, 200, 400, 10000, 800, 500, 200 };
		images = new int[] { R.drawable.group10003, R.drawable.group10025,
				R.drawable.group10025, R.drawable.group10003,
				R.drawable.group10025, R.drawable.group10025,
				R.drawable.group10025, R.drawable.group10025 };

		GridView gridview_commend = (GridView) findViewById(R.id.gridview_commend);
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 8; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("images", images[i]);
			map.put("groupName", groupName[i]);
			map.put("topicCount", topicCount[i]);
			lstImageItem.add(map);
		}

		SimpleAdapter saImageItems = new SimpleAdapter(this,
				lstImageItem,// 数据源
				R.layout.item,// 显示布局
				new String[] { "images", "groupName", "topicCount" },
				new int[] { R.id.group_icon, R.id.group_name,
						R.id.group_topic_count });
		gridview_commend.setAdapter(saImageItems);
		gridview_commend.setOnItemClickListener(new ItemClickListener());

		initMenuBtn();
	}

	/**
	 * 初始化菜单按钮
	 */
	private void initMenuBtn() {
		signinBtn = (Button) findViewById(R.id.signin);
		blogBtn = (Button) findViewById(R.id.blog);
		signinBtn.setOnClickListener(new MenuBtnClick(signinBtn));
		blogBtn.setOnClickListener(new MenuBtnClick(blogBtn));
	}

	class MenuBtnClick implements OnClickListener {
		private Button btn;

		MenuBtnClick(Button btn) {
			this.btn = btn;
		}

		@Override
		public void onClick(View arg0) {
			Intent intent = null;
			if (btn == MainActivity.this.signinBtn) { // 吐槽
				intent = new Intent(MainActivity.this, SigninActivity.class);
			} else if (btn == MainActivity.this.blogBtn) {// 博客
				intent = new Intent(MainActivity.this, BlogActivity.class);
			}
			startActivity(intent);
		}
	}

	class ItemClickListener implements OnItemClickListener {
		/**
		 * 点击项时触发事件
		 * 
		 * @param parent
		 *            发生点击动作的AdapterView
		 * @param view
		 *            在AdapterView中被点击的视图(它是由adapter提供的一个视图)。
		 * @param position
		 *            视图在adapter中的位置。
		 * @param rowid
		 *            被点击元素的行id。
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long rowid) {
			HashMap<String, Object> item = (HashMap<String, Object>) parent
					.getItemAtPosition(position);
			// 获取数据源的属性值
			String itemText = (String) item.get("itemText");
			Object object = item.get("itemImage");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
