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

	public ArticleListAdapter(Context context, List<Article> list) {
		this.context = context;
		this.list = list;
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
		return list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.article_item,
					null);
			TextView titView = (TextView) view.findViewById(R.id.article_title);
			TextView authorView = (TextView) view
					.findViewById(R.id.article_author);
			TextView timeView = (TextView) view.findViewById(R.id.article_time);
			TextView recountView = (TextView) view
					.findViewById(R.id.article_recount);
			Article article = list.get(position);
			titView.setText(article.getTitle());
			authorView.setText(article.getAuthorName());
			timeView.setText(article.getPostTime() == null ? "" : article
					.getPostTime().substring(0, 16));
			recountView.setText(article.getReNumber());
		}
		return view;
	}
}
