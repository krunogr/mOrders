package com.example.mnarudzbe.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mnarudzbe.R;
import com.example.mnarudzbe.items.PlaceItem;

public class PlacesAdapter extends ArrayAdapter<PlaceItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<PlaceItem> data = new ArrayList<PlaceItem>();

	public PlacesAdapter(Context context, int layoutResourceId,
			ArrayList<PlaceItem> data) {
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
			holder.name = (TextView) row.findViewById(R.id.place_name_textview);
			holder.address = (TextView) row
					.findViewById(R.id.place_address_textview);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		PlaceItem item = data.get(position);
		holder.name.setText(item.getName());
		holder.address.setText(item.getAddress());
		return row;
	}

	static class ViewHolder {
		TextView name, address;
	}
}