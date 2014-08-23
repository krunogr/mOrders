package com.example.mnarudzbe;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class WebServiceAsyncTask extends AsyncTask<Object, Void, Object> {
	String url;
	public static final int POST_TASK = 1;
	public static final int GET_TASK = 2;

	private int taskType = GET_TASK;
	Context context;

	private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	private ProgressDialog pDlg = null;

	public WebServiceAsyncTask(String url, int taskType, Context context) {
		super();
		this.url = url;
		this.taskType = taskType;
		this.context = context;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		String jsonResult = "[]";
		HttpClient client = new DefaultHttpClient();
		Object[] result = null;
		switch (taskType) {
		case POST_TASK:
			HttpPost httpPost = new HttpPost(url);
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				client.execute(httpPost);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case GET_TASK:
			HttpGet httpGet = new HttpGet(url);
			ResponseHandler<String> handler = new BasicResponseHandler();
			try {
				jsonResult = client.execute(httpGet, handler);
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.getConnectionManager().shutdown();
			result = new Object[] { jsonResult };
		}
		return result;
	}

	public void addNameValuePair(String name, String value) {

		params.add(new BasicNameValuePair(name, value));
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		pDlg.dismiss();
	}

	private void showProgressDialog() {
		pDlg = new ProgressDialog(context);
		pDlg.setMessage("Molimo prièekajte trenutak..");
		pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDlg.setCancelable(false);
		pDlg.show();

	}

	@Override
	protected void onPreExecute() {

		showProgressDialog();
	}
}
