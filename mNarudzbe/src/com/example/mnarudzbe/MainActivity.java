package com.example.mnarudzbe;

import java.util.ArrayList;

import android.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mnarudzbe.items.ArticleItem;
import com.example.mnarudzbe.items.PlaceItem;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private static final int PLACES_FRAGMENT = 0, EVENT_FRAGMENT = 1,
			HISTORY_FRAGMENT = 2;

	public static ArrayList<PlaceItem> placesList = null;
	public static ArrayList<ArticleItem> listForOrder = null;
	public static String USER = null;
	public ActionBar actionBar;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);

		init_UI();
		return true;
	}

	private void init_UI() {
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about_action:
			// about
			new AlertDialog.Builder(this)
					.setTitle(R.string.about_app)
					.setMessage(R.string.about_app_text)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			break;
		case R.id.action_exit_action:
			finish();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());

		FragmentManager fm = getSupportFragmentManager();
		for (Fragment frag : fm.getFragments()) {
			if (frag.isVisible()) {
				FragmentManager childFm = frag.getChildFragmentManager();
				for (Fragment fragChild : childFm.getFragments()) {
					childFm.popBackStack();

				}
			}
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			switch (position) {
			case PLACES_FRAGMENT:
				fragment = new RootFragment();
				break;
			case EVENT_FRAGMENT:
				fragment = new RootFragment_Event();
				break;
			case HISTORY_FRAGMENT:
				fragment = new RootFragment_History();
				break;
			default:
				fragment = new Fragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.places);
			case 1:
				return getString(R.string.ads);
			case 2:
				return getString(R.string.history);
			}
			return null;
		}

	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		for (Fragment frag : fm.getFragments()) {
			if (frag.isVisible()) {
				FragmentManager childFm = frag.getChildFragmentManager();
				if (childFm.getBackStackEntryCount() > 0) {
					childFm.popBackStack();
					return;

				}
			}
		}
		super.onBackPressed();
	}

}
