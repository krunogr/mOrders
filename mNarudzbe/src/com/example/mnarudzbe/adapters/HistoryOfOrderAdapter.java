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

import com.example.mnarudzbe.items.OrderItem;

public class HistoryOfOrderAdapter extends ArrayAdapter<OrderItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<OrderItem> data = new ArrayList<OrderItem>();

	public HistoryOfOrderAdapter(Context context, int layoutResourceId,
			ArrayList<OrderItem> data) {
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
			holder.nameOfPlace = (TextView) row
					.findViewById(R.id.place_name_textview);
			holder.otherDetails = (TextView) row
					.findViewById(R.id.place_address_textview);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		OrderItem item = data.get(position);
		String totalString = item.getPrice();
		totalString = ((totalString.indexOf(".")) == totalString.length() - 2) ? totalString += 0
				: totalString;
		String parts[] = item.getTime().split(" ");
		String time = parts[0].replace(".", "/") + "  "
				+ parts[1].replace(".", ":");
		holder.nameOfPlace.setText(item.getNameOfPlace());
		holder.otherDetails.setText("Datum raèuna: " + time
				+ "\nIznos raèuna: " + totalString + "kn");
		return row;
	}

	static class ViewHolder {
		TextView nameOfPlace, otherDetails;
	}
}