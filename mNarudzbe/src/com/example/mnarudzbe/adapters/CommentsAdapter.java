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

import com.example.mnarudzbe.items.CommentItem;

public class CommentsAdapter extends ArrayAdapter<CommentItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<CommentItem> data = new ArrayList<CommentItem>();

	public CommentsAdapter(Context context, int layoutResourceId,
			ArrayList<CommentItem> data) {
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
			holder.date = (TextView) row
					.findViewById(R.id.comment_date_textview);
			holder.textOfComment = (TextView) row
					.findViewById(R.id.comment_text_textview);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		CommentItem item = data.get(position);
		String parts[] = item.getTime().split(" ");

		String time = parts[0].replace(".", "/") + "  "
				+ parts[1].replace(".", ":");
		holder.date.setText("Datum komentara: " + time);
		holder.textOfComment.setText(item.getTextOfComment());
		return row;
	}

	static class ViewHolder {
		TextView date, textOfComment;
	}
}