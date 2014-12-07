package com.example.mnarudzbe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mnarudzbe.adapters.OfferAdapter;
import com.example.mnarudzbe.items.ArticleItem;
import com.example.mnarudzbe.loaders.JsonArticlesLoader;

public class ViewOfferFragment extends Fragment implements OnClickListener,
		OnChildClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	ImageButton button_offer, button_info, button_comments, button_passToOrder;
	ExpandableListView expandListView;
	Map<String, ArrayList<ArticleItem>> collection;
	private ArrayList<ArticleItem> itemList,
			listForOrder = new ArrayList<ArticleItem>();
	private List<String> typeNames;
	private ArrayList<ArticleItem> articlesList;
	public String user;
	public View chooseAmount;
	private AlertDialog amountDialog;
	private NumberPicker numberPickerAmount;
	private int groupPosition;
	List<ArticleItem> listofArticlesForUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		articlesList = new ArrayList<ArticleItem>();
		JsonArticlesLoader jsonArticlesLoader = new JsonArticlesLoader(
				getActivity());
		articlesList = (ArrayList<ArticleItem>) jsonArticlesLoader
				.getArticles();
		user = MainActivity.USER;
		chooseAmount = LayoutInflater.from(getActivity()).inflate(
				R.layout.alert_choose_amount, null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_offer,
				container, false);
		button_offer = (ImageButton) rootView.findViewById(R.id.button_order);
		button_info = (ImageButton) rootView.findViewById(R.id.button_info);
		button_comments = (ImageButton) rootView
				.findViewById(R.id.button_comments);
		button_passToOrder = (ImageButton) rootView
				.findViewById(R.id.button_passToOrder);
		expandListView = (ExpandableListView) rootView
				.findViewById(R.id.expandableListView);

		getDataFromSQL();
		expandListView.setAdapter(new OfferAdapter(getActivity(),
				R.layout.grouprow, R.layout.childrow, getDataFromSQL(),
				typeNames));
		numberPickerAmount = (NumberPicker) chooseAmount
				.findViewById(R.id.numberPicker_amount);

		expandListView.setOnChildClickListener(this);
		button_offer.setOnClickListener(this);
		button_info.setOnClickListener(this);
		button_comments.setOnClickListener(this);
		button_passToOrder.setOnClickListener(this);

		return rootView;
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
		case R.id.button_passToOrder:
			checkArticlesForOrder();

			fragment = new ViewOrderFragment();

			break;
		default:
			break;
		}
		if (listForOrder.size() != 0 || viewId != R.id.button_passToOrder) {
			FragmentTransaction trans = getFragmentManager().beginTransaction();

			trans.replace(R.id.root_frame, fragment);
			trans.addToBackStack(null);
			trans.commit();
		} else {
			Toast.makeText(getActivity(), R.string.choose_something,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void checkArticlesForOrder() {
		listForOrder = new ArrayList<ArticleItem>();
		for (ArticleItem element : listofArticlesForUser) {
			if (element.getAmount() != 0) {

				listForOrder.add(element);
			}
		}
		MainActivity.listForOrder = listForOrder;
	}

	public Map<String, ArrayList<ArticleItem>> getDataFromSQL() {

		listofArticlesForUser = new ArrayList<ArticleItem>();
		typeNames = new ArrayList();
		for (ArticleItem element : articlesList) {
			if (element.getUser().equals(user)) {
				listofArticlesForUser.add(element);
			}
		}

		collection = new HashMap<String, ArrayList<ArticleItem>>();
		String check = "delete";
		itemList = new ArrayList<ArticleItem>();
		for (ArticleItem element : listofArticlesForUser) {
			if (!element.getType().equals(check)) {
				collection.put(check, itemList);
				itemList = new ArrayList<ArticleItem>();
				typeNames.add(element.getType());
			}

			itemList.add(element);
			check = element.getType();

		}

		typeNames.remove("delete");
		collection.remove("delete");
		collection.put(check, itemList);

		return collection;

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		ArticleItem articleItem = collection.get(typeNames.get(groupPosition))
				.get(childPosition);
		createDialog(articleItem);
		this.groupPosition = groupPosition;
		Log.d("myTag", "ddasd");

		return false;
	}

	public void createDialog(final ArticleItem articleItem) {

		amountDialog = new AlertDialog.Builder(getActivity())
				.setView(chooseAmount)
				.setTitle(R.string.choose_amount)
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
								articleItem.setAmount(numberPickerAmount
										.getValue());
								expandListView.setAdapter(new OfferAdapter(
										getActivity(), R.layout.grouprow,
										R.layout.childrow, getDataFromSQL(),
										typeNames));

							}
						}).create();

		amountDialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {

				expandListView.expandGroup(groupPosition);
				((ViewGroup) chooseAmount.getParent()).removeView(chooseAmount);

			}
		});

		amountDialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				String[] nums = new String[21];
				for (int i = 0; i < nums.length; i++)
					nums[i] = Integer.toString(i);

				numberPickerAmount.setMinValue(0);
				numberPickerAmount.setMaxValue(20);
				numberPickerAmount.setWrapSelectorWheel(true);
				numberPickerAmount.setDisplayedValues(nums);
				numberPickerAmount.setValue(1);
				numberPickerAmount.setOrientation(2);

			}
		});
		amountDialog.show();
	}
}