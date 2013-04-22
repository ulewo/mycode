package com.ulewo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.Blog;

public class BlogListAdapter extends BaseAdapter {

	private List<Blog> list = null;

	private Context context;

	private LayoutInflater mInflater;

	public BlogListAdapter(Context context, List<Blog> list) {

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
			return list.get(position).getId();
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
			view = this.mInflater.inflate(R.layout.article_item, null);
		}
		else {
			view = convertView;
		}
		bindView(position, view);
		return view;
	}

	private void bindView(int postion, View view) {

		Blog blog = list.get(postion);
		TextView titView = (TextView) view.findViewById(R.id.article_title);
		TextView authorView = (TextView) view.findViewById(R.id.article_author);
		TextView timeView = (TextView) view.findViewById(R.id.article_time);
		TextView recountView = (TextView) view.findViewById(R.id.article_recount);

		titView.setText(blog.getTitle());
		authorView.setText(blog.getAuthorName());
		timeView.setText(blog.getPostTime());
		recountView.setText(blog.getReNumber() + "");
	}

	public void loadMore(List<Blog> blogList) {

		list.addAll(blogList);
		this.notifyDataSetChanged();
	}

}
