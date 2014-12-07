package com.example.mnarudzbe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import android.R;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.mnarudzbe.adapters.HistoryOfOrderAdapter;
import com.example.mnarudzbe.items.ArticleItem;
import com.example.mnarudzbe.items.OrderItem;

public class ViewHistoryFragment extends Fragment implements
		OnItemClickListener {
	private HistoryOfOrderAdapter historyOfOrderAdapter;
	private ListView listView_places;
	private ArrayList<OrderItem> ordersList;
	private ArrayList<ArticleItem> articlesList;
	public static String USER;
	private String path;

	public static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_view_places,
				container, false);
		initUI(rootView);

		return rootView;
	}

	private void initUI(View rootView) {

		ordersList = new ArrayList<OrderItem>();
		path = Environment.getExternalStorageDirectory() + "/"
				+ getResources().getString(R.string.application_path);
		listView_places = (ListView) rootView
				.findViewById(R.id.listView_places);
		getOldOrders();
		historyOfOrderAdapter = new HistoryOfOrderAdapter(getActivity(),
				R.layout.places_row, ordersList);
		listView_places.setAdapter(historyOfOrderAdapter);
		listView_places.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		MainActivity.listForOrder = ((OrderItem) arg0.getItemAtPosition(arg2))
				.getListForOrder();

		FragmentTransaction trans = getFragmentManager().beginTransaction();
		Fragment fragment = new ViewOrderFragment();

		Bundle args = new Bundle();
		args.putString("total",
				((OrderItem) arg0.getItemAtPosition(arg2)).getPrice());
		args.putString("user",
				((OrderItem) arg0.getItemAtPosition(arg2)).getUser());
		args.putString("place",
				((OrderItem) arg0.getItemAtPosition(arg2)).getNameOfPlace());
		fragment.setArguments(args);

		trans.replace(R.id.root_frame_history, fragment);
		 trans.addToBackStack(null);
		trans.commit();

	}

	public void getOldOrders() {
		File historyOfOrderFile = new File(path + File.separator
				+ "history_of_orders.txt");
		ordersList = new ArrayList<OrderItem>();
		ArrayList<OrderItem> helpOrdersList = new ArrayList<OrderItem>();
		try {
			Scanner sc = new Scanner(historyOfOrderFile);

			while (sc.hasNextLine()) {
				articlesList = new ArrayList<ArticleItem>();
				String order = sc.nextLine();
				String parts_type[] = order.split("%");
				String parts_articles[] = parts_type[0].split("#");

				for (String element : parts_articles) {
					String parts_of_article[] = element.split(" X");
					ArticleItem articleItem = new ArticleItem(
							parts_of_article[0],
							Integer.parseInt(parts_of_article[1]));
					articlesList.add(articleItem);

				}
				helpOrdersList.add(new OrderItem(parts_type[2], articlesList,
						parts_type[3], parts_type[1], parts_type[4]));

			}
			sc.close();

			for (int i = helpOrdersList.size() - 1; i >= 0; i--) {
				ordersList.add(helpOrdersList.get(i));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}