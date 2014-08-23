package com.example.mnarudzbe.loaders;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.mnarudzbe.WebServiceAsyncTask;
import com.example.mnarudzbe.items.EventItem;

public class JsonEventsLoader {
	static String URL = "http://mnarudzbe.mnarudzbe.eu.cloudbees.net/rest/Resources/allEvents";
	// static String URL =
	// "http://192.168.1.28:8084/mNarudzbe_web/rest/Resources/allEvents";
	Context context;

	public JsonEventsLoader(Context context) {
		this.context = context;
	}

	public List<EventItem> getEvents() {
		String jsonResult = getData();
		List<EventItem> listAllEvents = parseData(jsonResult);
		return listAllEvents;
	}

	private String getData() {
		WebServiceAsyncTask asyncTask = new WebServiceAsyncTask(URL,
				WebServiceAsyncTask.GET_TASK, context);
		asyncTask.execute(new Object[] {});
		String jsonResult = "[]";
		try {
			jsonResult = (String) ((Object[]) asyncTask.get())[0];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonResult;
	}

	private List<EventItem> parseData(String jsonArrayString) {
		List<EventItem> allEvents = new ArrayList<EventItem>();
		try {
			JSONArray jEvents = new JSONArray(jsonArrayString);

			for (int i = 0; i < jEvents.length(); i++) {
				JSONObject jEvent = jEvents.getJSONObject(i);

				String place = jEvent.getString("place");
				String date_of_event = jEvent.getString("datum_eventa");
				String name = jEvent.getString("name");
				String description_of_event = jEvent.getString("description");
				EventItem eventItem = new EventItem(place, date_of_event, name,
						description_of_event);
				allEvents.add(eventItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allEvents;
	}

}
