package com.example.mnarudzbe;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mnarudzbe.items.PlaceItem;

public class ViewInfoFragment extends Fragment implements OnClickListener {

	ImageButton button_offer, button_info, button_comments;
	TextView address, name, email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_info,
				container, false);
		button_offer = (ImageButton) rootView.findViewById(R.id.button_order);
		button_info = (ImageButton) rootView.findViewById(R.id.button_info);
		button_comments = (ImageButton) rootView
				.findViewById(R.id.button_comments);
		address = (TextView) rootView.findViewById(R.id.textView_info_address);
		name = (TextView) rootView.findViewById(R.id.textView_info_name);
		email = (TextView) rootView.findViewById(R.id.textView_info_email);
		button_offer.setOnClickListener(this);
		button_info.setOnClickListener(this);
		button_comments.setOnClickListener(this);
		checkDate();
		return rootView;
	}

	private void checkDate() {
		ArrayList<PlaceItem> placesList = MainActivity.placesList;
		for (PlaceItem element : placesList) {
			if (MainActivity.USER.equals(element.getUser())) {
				address.setText(element.getAddress());
				name.setText(element.getName());
				email.setText(element.getEmail());
				break;
			}

		}

	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		Fragment fragment = null;
		switch (viewId) {
		case R.id.button_order:
			fragment = new ViewOfferFragment();
			break;
		case R.id.button_info:
			fragment = new ViewInfoFragment();
			break;
		case R.id.button_comments:
			fragment = new ViewCommentsFragment();
			break;
		default:
			break;
		}
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.addToBackStack(null);
		trans.replace(R.id.root_frame, fragment);
		trans.commit();
	}

}