package com.ulewo.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.Group;

public class GroupListAdapter extends BaseAdapter {

	private List<Group> list = null;

	private Context context;

	private LayoutInflater mInflater;

	public GroupListAdapter(Context context, List<Group> list) {

		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {

		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {

		if (position < getCount()) {
			return Long.valueOf(list.get(position).getGid());
		}
		else {
			return 0;
		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return createViewFromResource(position, convertView);
	}

	private View createViewFromResource(int position, View convertView) {

		View view;
		if (convertView == null) {
			view = this.mInflater.inflate(R.layout.group_item, null);
		}
		else {
			view = convertView;
		}
		bindView(position, view);
		return view;
	}

	private void bindView(int postion, View view) {

		Group blog = list.get(postion);
		ImageView imageView = (ImageView) view.findViewById(R.id.wowo_icon);
		TextView titView = (TextView) view.findViewById(R.id.wowo_tit);
		TextView authorView = (TextView) view.findViewById(R.id.wowo_username_con);
		TextView memberView = (TextView) view.findViewById(R.id.wowo_member_con);
		TextView articleView = (TextView) view.findViewById(R.id.wowo_articlecount_con);

		//imageView.setImageBitmap(returnBitMap(blog.getGroupIcon()));
		titView.setText(blog.getgName());
		authorView.setText(blog.getgUserName());
		memberView.setText(blog.getgMember());
		articleView.setText(blog.getgArticleCount());
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

	public void loadMore(List<Group> groupList) {

		list.addAll(groupList);
		this.notifyDataSetChanged();
	}

}
