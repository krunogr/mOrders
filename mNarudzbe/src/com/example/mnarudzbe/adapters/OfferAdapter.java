package com.example.mnarudzbe.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnarudzbe.R;
import com.example.mnarudzbe.items.ArticleItem;

public class OfferAdapter extends BaseExpandableListAdapter {

	private Context context;
	private int layoutResourceGroupId, layoutResourceChildId;
	Map<String, ArrayList<ArticleItem>> collection;
	private List<String> types;


	public OfferAdapter(Context context, int layoutResourceGroupId,
			int layoutResourceChildId,
			Map<String, ArrayList<ArticleItem>> collection, List<String> types) {
		this.layoutResourceGroupId = layoutResourceGroupId;
		this.layoutResourceChildId = layoutResourceChildId;
		this.context = context;
		this.collection = collection;
		this.types = types;

	}

	@Override
	public int getGroupCount() {
		return types.size();
	}

	@Override
	public int getChildrenCount(int i) {
		return collection.get(types.get(i)).size();
	}

	@Override
	public Object getGroup(int i) {
		return types.get(i);
	}

	@Override
	public ArticleItem getChild(int i, int i1) {
		return collection.get(types.get(i)).get(i1);

	}

	@Override
	public long getGroupId(int i) {
		return i;
	}

	@Override
	public long getChildId(int i, int i1) {
		return i1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolderChild holderChild = null;
		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceChildId, parent, false);
			holderChild = new ViewHolderChild();
			holderChild.name = (TextView) row
					.findViewById(R.id.textView_product);
			holderChild.price = (TextView) row
					.findViewById(R.id.textView_price);
			holderChild.amount = (TextView) row
					.findViewById(R.id.textView_amount_number);
			holderChild.layoutAmount =(LinearLayout) row.findViewById(R.id.layout_amount);
			row.setTag(holderChild);
		}

		else {
			holderChild = (ViewHolderChild) row.getTag();
		}
		holderChild.name.setText(getChild(groupPosition, childPosition)
				.getName());
		holderChild.price.setText("Cijena: "
				+ String.valueOf(getChild(groupPosition, childPosition)
						.getPrice()) + " kn");
		String amount = String.valueOf(getChild(groupPosition, childPosition)
				.getAmount());

		if (!amount.equals("0")) {
			holderChild.amount.setText(amount);
			holderChild.layoutAmount.setVisibility(View.VISIBLE);
		}
		else {
			holderChild.layoutAmount.setVisibility(View.INVISIBLE);
		}


		return row;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolderGroup holderGroup = null;
		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceGroupId, parent, false);
			holderGroup = new ViewHolderGroup();
			holderGroup.name = (TextView) row.findViewById(R.id.textView_group);
			row.setTag(holderGroup);
		}

		else {
			holderGroup = (ViewHolderGroup) row.getTag();
		}
		holderGroup.name.setText("      " + getGroup(groupPosition).toString());
		return row;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	static class ViewHolderGroup {
		TextView name;
	}

	static class ViewHolderChild {
		TextView name, price, amount;
		LinearLayout layoutAmount;
	}

}
