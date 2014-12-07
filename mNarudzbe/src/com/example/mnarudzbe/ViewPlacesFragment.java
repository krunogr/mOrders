package com.example.mnarudzbe;

import java.util.ArrayList;

import android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.mnarudzbe.adapters.PlacesAdapter;
import com.example.mnarudzbe.items.PlaceItem;
import com.example.mnarudzbe.loaders.JsonPlacesLoader;

public class ViewPlacesFragment extends Fragment implements OnItemClickListener {
	private PlacesAdapter placesAdapter;
	private ListView listView_places;
	private ArrayList<PlaceItem> placesList;
	public static String USER;

	public static final String ARG_SECTION_NUMBER = "section_number";

	public ViewPlacesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_places,
				container, false);
		initUI(rootView);

		return rootView;
	}

	private void initUI(View rootView) {
		if (MainActivity.placesList == null) {

			placesList = new ArrayList<PlaceItem>();
			JsonPlacesLoader placesLoader = new JsonPlacesLoader(getActivity());
			placesList = (ArrayList<PlaceItem>) placesLoader.getPlaces();

			MainActivity.placesList = placesList;
		} else {

			placesList = MainActivity.placesList;
		}
		listView_places = (ListView) rootView
				.findViewById(R.id.listView_places);

		placesAdapter = new PlacesAdapter(getActivity(), R.layout.places_row,
				placesList);
		listView_places.setAdapter(placesAdapter);
		listView_places.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		MainActivity.USER = ((PlaceItem) arg0.getItemAtPosition(arg2))
				.getUser();
		trans.replace(R.id.root_frame, new ViewOfferFragment());
		trans.addToBackStack(null);
		trans.commit();

	}

	public static String getArgSectionNumber() {
		return ARG_SECTION_NUMBER;
	}

}