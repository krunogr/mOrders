package com.example.mnarudzbe.loaders;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.mnarudzbe.WebServiceAsyncTask;
import com.example.mnarudzbe.items.PlaceItem;

public class JsonPlacesLoader {
	static String URL = "http://mnarudzbe.mnarudzbe.eu.cloudbees.net/rest/Resources/allPlaces";
	// static String URL =
	// "http://192.168.1.28:8084/mNarudzbe_web/rest/Resources/allPlaces";
	Context context;

	public JsonPlacesLoader(Context context) {
		this.context = context;
	}

	public List<PlaceItem> getPlaces() {
		String jsonResult = getData();
		List<PlaceItem> listAllPlaces = parseData(jsonResult);
		return listAllPlaces;
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

	private List<PlaceItem> parseData(String jsonArrayString) {
		List<PlaceItem> allPlaces = new ArrayList<PlaceItem>();
		try {
			JSONArray jPlaces = new JSONArray(jsonArrayString);

			for (int i = 0; i < jPlaces.length(); i++) {
				JSONObject jArticle = jPlaces.getJSONObject(i);
				String name = jArticle.getString("naziv");
				String address = jArticle.getString("adresa");
				String email = jArticle.getString("email");
				String user = jArticle.getString("user");
				PlaceItem placeItem = new PlaceItem(name, address, email, user);
				allPlaces.add(placeItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allPlaces;
	}

}
