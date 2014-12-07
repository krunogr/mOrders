package com.example.mnarudzbe.adapters;

import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mnarudzbe.items.ArticleItem;

public class SubmitOrderAdapter extends ArrayAdapter<ArticleItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ArticleItem> data = new ArrayList<ArticleItem>();

	public SubmitOrderAdapter(Context context, int layoutResourceId,
			ArrayList<ArticleItem> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) row.findViewById(R.id.textView_article);
			holder.amount = (TextView) row.findViewById(R.id.textView_article_amount);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		ArticleItem item = data.get(position);
		holder.name.setText(item.getName());
		holder.amount.setText("Kolièina: " + String.valueOf(item.getAmount()));
		return row;
	}

	static class ViewHolder {
		TextView name, amount;
	}
}