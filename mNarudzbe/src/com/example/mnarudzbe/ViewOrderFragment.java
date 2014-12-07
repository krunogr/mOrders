package com.example.mnarudzbe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnarudzbe.adapters.SubmitOrderAdapter;
import com.example.mnarudzbe.items.ArticleItem;
import com.example.mnarudzbe.items.PlaceItem;

public class ViewOrderFragment extends Fragment implements OnClickListener {

	static String URL = "http://mnarudzbejava-krunogr.rhcloud.com/rest/Resources";
	// static String URL =
	// "http://192.168.1.28:8084/mNarudzbe_web/rest/Resources";
	public static final String ARG_SECTION_NUMBER = "section_number";
	ImageButton button_offer, button_info, button_comments;
	private SubmitOrderAdapter submitOrderAdapter;
	private ListView listView_article;
	private ArrayList<ArticleItem> listForOrder;
	private TextView textView_receiptTotal;
	private AlertDialog amountDialog;
	private NumberPicker numberPickerAmount;
	private View chooseAmount;
	private ImageButton button_submitOrder;
	float total = 0;
	private String path;
	String user = null;
	String name = "";
	Date dateOfOrder;
	DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_order,
				container, false);

		listForOrder = MainActivity.listForOrder;

		initUI(rootView);
		path = Environment.getExternalStorageDirectory() + "/"
				+ getResources().getString(R.string.application_path);
		return rootView;
	}

	public void initUI(View rootView) {
		listView_article = (ListView) rootView
				.findViewById(R.id.listView_submitOrder);
		textView_receiptTotal = (TextView) rootView
				.findViewById(R.id.textView_receiptTotal);

		try {
			total = Float.parseFloat(getArguments().getString("total"));
			user = getArguments().getString("user");

		} catch (Exception e) {

		}
		if (total == 0) {
			total = calculateValueOfReceipt();
		}

		String totalString = String.valueOf(total);
		totalString = ((totalString.indexOf(".")) == totalString.length() - 2) ? totalString += 0
				: totalString;

		textView_receiptTotal.setText("Iznos raèuna: " + totalString + " kn");
		submitOrderAdapter = new SubmitOrderAdapter(getActivity(),
				R.layout.article_row, listForOrder);
		chooseAmount = LayoutInflater.from(getActivity()).inflate(
				R.layout.alert_choose_amount, null);
		listView_article.setAdapter(submitOrderAdapter);
		button_submitOrder = (ImageButton) rootView
				.findViewById(R.id.button_submitOrder);
		button_submitOrder.setOnClickListener(this);
		numberPickerAmount = (NumberPicker) chooseAmount
				.findViewById(R.id.numberPicker_amount);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.button_submitOrder:
			createDialog();
			break;

		default:
			break;
		}

	}

	public float calculateValueOfReceipt() {
		total = 0;
		for (ArticleItem element : listForOrder) {
			total += (Float.parseFloat(element.getPrice()) * element
					.getAmount());

		}

		return total;

	}

	public void createDialog() {

		amountDialog = new AlertDialog.Builder(getActivity())
				.setView(chooseAmount)
				.setTitle(R.string.choose_desk)
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						})

				.setPositiveButton(R.string.choose,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								WebServiceAsyncTask asyncTask = new WebServiceAsyncTask(
										URL, WebServiceAsyncTask.POST_TASK,
										getActivity());
								String narudzba = "";
								for (ArticleItem element : listForOrder) {
									narudzba += element.getName() + " X"
											+ element.getAmount() + "#";

								}
								dateOfOrder = new java.util.Date();
								asyncTask
										.addNameValuePair("narudzba", narudzba);
								asyncTask.addNameValuePair("brojMjesta", String
										.valueOf(numberPickerAmount.getValue()));
								asyncTask.addNameValuePair("racunNarudzbe",
										String.valueOf(total));
								asyncTask.addNameValuePair("vrijemeZaprimanja",
										df.format(dateOfOrder));
								asyncTask.addNameValuePair("user", listForOrder
										.get(0).getUser());
								asyncTask.execute(new String[] { URL });
								if (user == null) {
									user = listForOrder.get(0).getUser();
								}

								for (PlaceItem element : MainActivity.placesList) {
									if (element.getUser().equals(user)) {
										name = element.getName();
										break;

									}
								}

								String orderDetails = narudzba + "%" + total
										+ "%" + df.format(dateOfOrder) + "%"
										+ user + "%" + name + "\n";

								writeOrderInFile(orderDetails);

								FragmentTransaction trans = getFragmentManager()
										.beginTransaction();
								trans.replace(R.id.root_frame,
										new ViewPlacesFragment());
								trans.addToBackStack(null);
								trans.commit();
								Toast.makeText(getActivity(),
										R.string.order_is_stored,
										Toast.LENGTH_SHORT).show();

							}

						}).create();

		amountDialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {

				((ViewGroup) chooseAmount.getParent()).removeView(chooseAmount);

			}
		});

		amountDialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				String[] nums = new String[20];
				for (int i = 0; i < nums.length; i++)
					nums[i] = Integer.toString(i);

				numberPickerAmount.setMinValue(1);
				numberPickerAmount.setMaxValue(20);
				numberPickerAmount.setWrapSelectorWheel(true);
				numberPickerAmount.setDisplayedValues(nums);
				numberPickerAmount.setValue(1);
				numberPickerAmount.setOrientation(2);

			}
		});
		amountDialog.show();
	}

	private void writeOrderInFile(String orderDetails) {

		File appDirectory = new File(path);
		File historyOfOrderFile = new File(path + File.separator
				+ "history_of_orders.txt");

		try {
			if (!appDirectory.exists()) {
				appDirectory.mkdir();

				historyOfOrderFile.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(historyOfOrderFile, true);
			fileWriter.write(orderDetails);
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences("latest_order", Context.MODE_PRIVATE);
		String date = String.valueOf(df.format(dateOfOrder));
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("dateOfOrder", date);
		editor.putLong("dateOfOrderSec", dateOfOrder.getTime());
		editor.putString("user", user);
		editor.putString("name", name);
		editor.commit();

	}
}