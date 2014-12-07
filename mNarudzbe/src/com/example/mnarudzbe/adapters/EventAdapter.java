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

import com.example.mnarudzbe.items.EventItem;

public class EventAdapter extends ArrayAdapter<EventItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<EventItem> data = new ArrayList<EventItem>();

	public EventAdapter(Context context, int layoutResourceId,
			ArrayList<EventItem> data) {
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
			holder.name = (TextView) row.findViewById(R.id.event_name_textview);
			holder.place = (TextView) row
					.findViewById(R.id.event_place_textview);
			holder.date = (TextView) row.findViewById(R.id.event_date_textview);
			holder.description = (TextView) row
					.findViewById(R.id.event_description_textview);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		EventItem item = data.get(position);
		holder.name.setText(item.getName());
		holder.place.setText("Gdje: " + item.getPlace());

		String parts[] = item.getDatum_eventa().split(" ");
		String time = parts[0].replace(".", "/") + "  "
				+ parts[1].replace(".", ":");

		holder.date.setText("Kada: " + time);
		holder.description.setText(item.getDescription());
		return row;
	}

	static class ViewHolder {
		TextView name, place, date, description;
	}
}