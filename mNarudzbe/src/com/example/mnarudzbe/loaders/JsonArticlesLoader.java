package com.example.mnarudzbe.loaders;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.mnarudzbe.WebServiceAsyncTask;
import com.example.mnarudzbe.items.ArticleItem;

public class JsonArticlesLoader {
	static String URL = "http://mnarudzbe.mnarudzbe.eu.cloudbees.net/rest/Resources/allArticles";
	// static String URL =
	// "http://192.168.1.28:8084/mNarudzbe_web/rest/Resources/allArticles";
	Context context;

	public JsonArticlesLoader(Context context) {
		this.context = context;
	}

	public List<ArticleItem> getArticles() {
		String jsonResult = getData();
		List<ArticleItem> listAllArticles = parseData(jsonResult);
		return listAllArticles;
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

	private List<ArticleItem> parseData(String jsonArrayString) {
		List<ArticleItem> allArticles = new ArrayList<ArticleItem>();
		try {
			JSONArray jArticles = new JSONArray(jsonArrayString);

			for (int i = 0; i < jArticles.length(); i++) {
				JSONObject jArticle = jArticles.getJSONObject(i);
				String name = jArticle.getString("name");
				String type = jArticle.getString("type");
				String user = jArticle.getString("user");
				String price = jArticle.getString("price");
				ArticleItem articleItem = new ArticleItem(name, type, price,
						user);
				allArticles.add(articleItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return allArticles;
	}

}
