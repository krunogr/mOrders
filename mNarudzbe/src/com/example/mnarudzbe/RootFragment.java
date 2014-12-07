package com.example.mnarudzbe;

import android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RootFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.root_fragment, container, false);

		FragmentTransaction transaction = getChildFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.root_frame, new ViewPlacesFragment());
		transaction.commit();

		return view;
	}

}
