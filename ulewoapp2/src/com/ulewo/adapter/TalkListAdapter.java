package com.ulewo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.Talk;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.StringUtils;

public class TalkListAdapter extends BaseAdapter {

	private ArrayList<Talk> list = null;

	private Context context;

	private LayoutInflater mInflater;

	private AsyncImageLoader asyncImageLoader = null;

	private ListView listView;

	public TalkListAdapter(Context context, ArrayList<Talk> list,
			AsyncImageLoader asyncImageLoader, ListView listView) {

		this.context = context;
		this.list = list;
		this.listView = listView;
		this.asyncImageLoader = asyncImageLoader;
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
			return Long.valueOf(list.get(position).getId());
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
			view = this.mInflater.inflate(R.layout.talk_item, null);
		} else {
			view = convertView;
		}
		bindView(position, view);
		return view;
	}

	private void bindView(int postion, View view) {

		final Talk talk = list.get(postion);
		ImageView imageView = (ImageView) view
				.findViewById(R.id.talk_user_icon);
		TextView authorView = (TextView) view.findViewById(R.id.talk_username);
		TextView content = (TextView) view.findViewById(R.id.talk_content);
		TextView talk_time = (TextView) view.findViewById(R.id.talk_time);
		TextView recount = (TextView) view.findViewById(R.id.talk_recount);

		// imageView.setImageBitmap(returnBitMap(blog.getGroupIcon()));
		String imageUrl = talk.getUserIcon();
		imageView.setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						ImageView imageViewByTag = (ImageView) listView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							if (null != imageDrawable) {
								imageViewByTag.setImageDrawable(imageDrawable);
							} else {
								imageViewByTag
										.setImageResource(R.drawable.icon);
							}
						}
					}
				}, true);
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.icon);
		} else {
			imageView.setImageDrawable(StringUtils.toRoundCornerDrawable(
					cachedImage, 5));
		}
		authorView.setText(talk.getUserName());
		authorView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showUserCenter(v.getContext(), talk.getUserId(),
						talk.getUserName());
			}
		});
		content.setText(talk.getContent());
		talk_time.setText(talk.getCreateTime());
		recount.setText(talk.getReCount() + "");
	}

	public void loadMore(ArrayList<Talk> talkList) {
		list.addAll(talkList);
		this.notifyDataSetChanged();
	}

}
