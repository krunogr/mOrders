package com.example.mnarudzbe;

import android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RootFragment_Event extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_fragment_event, container,
				false);

		FragmentTransaction transaction = getChildFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.root_frame_event, new ViewEventFragment());
		transaction.commit();

		return view;
	}

}
