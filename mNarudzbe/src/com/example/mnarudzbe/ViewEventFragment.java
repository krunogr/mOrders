package com.example.mnarudzbe;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mnarudzbe.adapters.EventAdapter;
import com.example.mnarudzbe.items.EventItem;
import com.example.mnarudzbe.loaders.JsonEventsLoader;

/**
 * This Fragment represent the tab to view the mData behind the collected points
 */
public class ViewEventFragment extends Fragment {
	private EventAdapter eventAdapter;
	private ListView listView_events;
	private ArrayList<EventItem> eventList;
	public static final String ARG_SECTION_NUMBER = "section_number";

	public ViewEventFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_ads, container,
				false);
		initUI(rootView);
		return rootView;
	}

	private void initUI(View rootView) {
		eventList = new ArrayList<EventItem>();
		JsonEventsLoader eventsLoader = new JsonEventsLoader(getActivity());
		eventList = (ArrayList<EventItem>) eventsLoader.getEvents();

		listView_events = (ListView) rootView.findViewById(R.id.listView_ads);

		eventAdapter = new EventAdapter(getActivity(), R.layout.event_rows,
				eventList);
		listView_events.setAdapter(eventAdapter);

	}

}