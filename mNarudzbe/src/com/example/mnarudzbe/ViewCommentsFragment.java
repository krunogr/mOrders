package com.example.mnarudzbe;

import java.util.ArrayList;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mnarudzbe.adapters.CommentsAdapter;
import com.example.mnarudzbe.items.CommentItem;
import com.example.mnarudzbe.loaders.JsonCommentsLoader;

public class ViewCommentsFragment extends Fragment implements OnClickListener {

	// static String URL = "http://192.168.1.28:8084/mNarudzbe_web/rest/Place";
	static String URL = "http://mnarudzbejava-krunogr.rhcloud.com/rest/Place";
	public static final String ARG_SECTION_NUMBER = "section_number";
	ImageButton button_offer, button_info, button_comments;
	private AlertDialog commentDialog;
	private View leaveComment;
	private ArrayList<CommentItem> allCommentList, commentListForUser;
	SharedPreferences sharedPreferences;
	CommentsAdapter commentsAdapter;
	ListView listView_comments;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_comments,
				container, false);
		leaveComment = LayoutInflater.from(getActivity()).inflate(
				R.layout.alert_leave_comment, null);

		allCommentList = new ArrayList<CommentItem>();
		JsonCommentsLoader commentsLoader = new JsonCommentsLoader(
				getActivity());
		allCommentList = (ArrayList<CommentItem>) commentsLoader.getComments();
		listView_comments = (ListView) rootView
				.findViewById(R.id.listView_comments);
		checkCommentForUser();
		commentsAdapter = new CommentsAdapter(getActivity(),
				R.layout.comment_row, commentListForUser);
		listView_comments.setAdapter(commentsAdapter);
		checkIfCanLeaveComment();

		button_offer = (ImageButton) rootView.findViewById(R.id.button_order);
		button_info = (ImageButton) rootView.findViewById(R.id.button_info);
		button_comments = (ImageButton) rootView
				.findViewById(R.id.button_comments);
		button_offer.setOnClickListener(this);
		button_info.setOnClickListener(this);
		button_comments.setOnClickListener(this);

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
		default:
			break;
		}
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.addToBackStack(null);
		trans.replace(R.id.root_frame, fragment);
		trans.commit();
	}

	public void checkIfCanLeaveComment() {

		try {
			sharedPreferences = getActivity().getSharedPreferences(
					"latest_order", Context.MODE_PRIVATE);
			long dateOfOrderSec = sharedPreferences
					.getLong("dateOfOrderSec", 0);
			long dateNowSec = new java.util.Date().getTime();
			if (dateNowSec - dateOfOrderSec > 3600000
					&& dateNowSec - dateOfOrderSec < 86400000) {
				createDialog();
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putLong("dateOfOrderSec", 1);

				editor.commit();
			}
		} catch (Exception e) {
		}

	}

	public void createDialog() {

		commentDialog = new AlertDialog.Builder(getActivity())
				.setView(leaveComment)
				.setTitle(R.string.leave_comment)
				.setNegativeButton(R.string.no_thanks,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})

				.setPositiveButton(R.string.save,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								EditText textOfComment = (EditText) leaveComment
										.findViewById(R.id.editText_leaveComment);

								String comment = textOfComment.getText()
										.toString();

								WebServiceAsyncTask asyncTask = new WebServiceAsyncTask(
										URL, WebServiceAsyncTask.POST_TASK,
										getActivity());
								asyncTask.addNameValuePair("comment", comment);
								String dateOfOrder = (sharedPreferences
										.getString("dateOfOrder", "0"));
								asyncTask.addNameValuePair("dateOfOrder",
										dateOfOrder);
								String user = (sharedPreferences.getString(
										"user", "0"));
								asyncTask.addNameValuePair("user", user);
								asyncTask
										.addNameValuePair("racunNarudzbe", "0");
								asyncTask.execute(new String[] { URL });
								commentDialog.dismiss();
								((ViewGroup) leaveComment.getParent())
										.removeView(leaveComment);

							}

						}).create();

		commentDialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {

			}
		});
		commentDialog.show();
	}

	public void checkCommentForUser() {
		commentListForUser = new ArrayList<CommentItem>();
		for (CommentItem element : allCommentList) {
			if (element.getUser().equals(MainActivity.USER)) {
				commentListForUser.add(element);

			}
		}

	}
}