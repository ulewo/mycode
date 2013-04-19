package com.ulewo.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.ReArticle;
import com.ulewo.handler.MxgsaTagHandler;

public class ReArticleListAdapter extends BaseAdapter {

	private List<ReArticle> list = null;

	private LayoutInflater mInflater;

	private LayoutInflater subflater;

	private Context context;

	private RelativeLayout reSubPanel = null;

	TextView reusers = null;

	Button subreformbtn = null;

	public ReArticleListAdapter(Context context, List<ReArticle> list, View subView) {

		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		subflater = LayoutInflater.from(context);
		reSubPanel = (RelativeLayout) subView;
		reusers = (TextView) reSubPanel.findViewById(R.id.reuser);
		subreformbtn = (Button) reSubPanel.findViewById(R.id.subreformbtn);
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
			view = this.mInflater.inflate(R.layout.rearticle_item, null);
		}
		else {
			view = convertView;
		}
		bindView(position, view);
		return view;
	}

	private void bindView(int postion, View view) {

		final ReArticle reArticle = list.get(postion);
		TextView recomment_username = (TextView) view.findViewById(R.id.recomment_username);
		TextView recomment_posttime = (TextView) view.findViewById(R.id.recomment_posttime);

		TextView recomment_con = (TextView) view.findViewById(R.id.recomment_con);
		TextPaint paint = recomment_username.getPaint();
		paint.setFakeBoldText(true);
		recomment_username.setText(reArticle.getAuthorName());
		recomment_posttime.setText(reArticle.getReTime());
		recomment_con.setText(Html.fromHtml(reArticle.getContent(), null, new MxgsaTagHandler(context)));

		//回复btn
		Button item_rebtn = (Button) view.findViewById(R.id.item_rebtn);
		item_rebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				reSubPanel.setVisibility(View.VISIBLE);
				reusers.setText(reArticle.getAuthorName());
			}
		});

		LinearLayout liner = (LinearLayout) view.findViewById(R.id.recomment_sub_layout);
		liner.removeAllViewsInLayout();
		List<ReArticle> childList = reArticle.getChildList();
		if (childList.size() > 0) {
			liner.setVisibility(View.VISIBLE);
		}
		else {
			liner.setVisibility(View.GONE);
		}
		View subview = null;
		for (ReArticle subRe : childList) {
			subview = this.subflater.inflate(R.layout.rearticle_sub_item, null);
			liner.addView(subview);
			TextView nameView = (TextView) subview.findViewById(R.id.recoment_sub_name);
			TextPaint paint1 = nameView.getPaint();
			paint1.setFakeBoldText(true);
			nameView.setText(subRe.getAuthorName());

			TextView atNameView = (TextView) subview.findViewById(R.id.recoment_sub_atname);
			TextPaint paint2 = atNameView.getPaint();
			paint2.setFakeBoldText(true);

			atNameView.setText(subRe.getAtUserName());

			TextView conView = (TextView) subview.findViewById(R.id.recoment_sub_con);
			conView.setText(Html.fromHtml(subRe.getContent(), null, new MxgsaTagHandler(context)));
			/*
			 * subview.setOnTouchListener(new OnTouchListener() {
			 * 
			 * @Override public boolean onTouch(View paramView, MotionEvent
			 * paramMotionEvent) {
			 * 
			 * if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			 * subview.setBackgroundResource(R.color.recomentsubpressed); } else
			 * { subview.setBackgroundResource(R.color.recomentsubnormal); }
			 * return true; } });
			 */
		}
	}

	public void loadMore(List<ReArticle> rearticleList) {

		list.addAll(rearticleList);
		this.notifyDataSetChanged();
	}

}
