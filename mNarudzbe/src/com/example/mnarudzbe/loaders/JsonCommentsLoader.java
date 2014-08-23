package com.example.mnarudzbe.loaders;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.mnarudzbe.WebServiceAsyncTask;
import com.example.mnarudzbe.items.CommentItem;

public class JsonCommentsLoader {
	static String URL = "http://mnarudzbe.mnarudzbe.eu.cloudbees.net/rest/Resources/allComments";
	// static String URL =
	// "http://192.168.1.28:8084/mNarudzbe_web/rest/Resources/allComments";
	Context context;

	public JsonCommentsLoader(Context context) {
		this.context = context;
	}

	public List<CommentItem> getComments() {
		String jsonResult = getData();
		List<CommentItem> listAllAComments = parseData(jsonResult);
		return listAllAComments;
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

	private List<CommentItem> parseData(String jsonArrayString) {
		List<CommentItem> allComments = new ArrayList<CommentItem>();
		try {
			JSONArray jComments = new JSONArray(jsonArrayString);

			for (int i = 0; i < jComments.length(); i++) {
				JSONObject jComment = jComments.getJSONObject(i);

				String user = jComment.getString("user");
				String time = jComment.getString("time");
				String textOfComment = jComment.getString("textOfComment");
				CommentItem commentItem = new CommentItem(user, time,
						textOfComment);
				allComments.add(commentItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allComments;
	}

}
