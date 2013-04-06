package com.ulewo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.Article;

public class ArticleListAdapter extends BaseAdapter {

	private List<Article> list = null;
	private Context context;

	private LayoutInflater mInflater;

	public ArticleListAdapter(Context context, List<Article> list) {
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
		} else {
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
		} else {
			view = convertView;
		}
		bindView(position, view);
		return view;
	}

	private void bindView(int postion, View view) {
		Article article = list.get(postion);
		TextView titView = (TextView) view.findViewById(R.id.article_title);
		TextView authorView = (TextView) view.findViewById(R.id.article_author);
		TextView timeView = (TextView) view.findViewById(R.id.article_time);
		TextView recountView = (TextView) view
				.findViewById(R.id.article_recount);

		titView.setText(article.getTitle());
		authorView.setText(article.getAuthorName());
		timeView.setText(article.getPostTime() == null ? "" : article
				.getPostTime().substring(0, 16));
		recountView.setText(article.getReNumber());
	}

	public void loadMore(List<Article> articleList) {
		list.addAll(articleList);
		this.notifyDataSetChanged();
	}

}
