package com.kosbrother.houseprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.kosbrother.houseprice.fragment.BreiefFragment;
import com.kosbrother.houseprice.fragment.BreifMonthFragment;
import com.kosbrother.houseprice.fragment.DetailFragment;
import com.kosbrother.houseprice.fragment.MonthSquarePriceFragment;
import com.kosbrother.houseprice.fragment.MonthTotalPriceFragment;
import com.kosbrother.houseprice.fragment.TransparentSupportMapFragment;
import com.kosbrother.houseprice.utils.TabManager;

public class MainActivity extends SherlockFragmentActivity
{

	private GoogleMap mGoogleMap;
	private TabHost mTabHost;
	private TabManager mTabManager;
	
	private LinearLayout mLayoutDataChange;
	private MyAdapter mAdapter;
	private ViewPager mPager;

	private Button btnList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mLayoutDataChange = (LinearLayout) findViewById(R.id.layout_data_change);
		mLayoutDataChange.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				if(mTabHost.getVisibility() == View.VISIBLE){
					mTabHost.setVisibility(View.GONE);
				}else {
					mTabHost.setVisibility(View.VISIBLE);
				}
			}
		});
		
		btnList = (Button) findViewById(R.id.button_list);
		btnList.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				BreiefFragment theBreiefFragment = mAdapter.getCurrBreiefFragment(mPager.getCurrentItem());
				theBreiefFragment.changeDetailView();
			}
		});
		
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);

		mTabManager.addTab(mTabHost.newTabSpec("simple").setIndicator("單價"),
				MonthSquarePriceFragment.class, null);
		mTabManager.addTab(mTabHost.newTabSpec("contacts").setIndicator("總價"),
				MonthTotalPriceFragment.class, null);

		if (savedInstanceState != null)
		{
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

		try
		{
			// Loading map
			initilizeMap();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void initilizeMap()
	{
		if (mGoogleMap == null)
		{
			mGoogleMap = ((TransparentSupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (mGoogleMap == null)
			{
				Toast.makeText(getApplicationContext(), "Sorry! unable to create maps",
						Toast.LENGTH_SHORT).show();
			}

			mGoogleMap.setOnCameraChangeListener(new OnCameraChangeListener()
			{

				@Override
				public void onCameraChange(CameraPosition arg0)
				{
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(),
					// "Camera Change " + arg0.target.toString(),
					// Toast.LENGTH_SHORT)
					// .show();
				}
			});

			mGoogleMap.setOnMapLoadedCallback(new OnMapLoadedCallback()
			{

				@Override
				public void onMapLoaded()
				{
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();
				}
			});

		}
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item)
	{

		switch (item.getItemId())
		{
		case R.id.menu_filter:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, FilterActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_query_record:
			// Toast.makeText(MainActivity.this, "list",
			// Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, ListActivity.class);
//			// intent.putExtras(bundle);
//			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	// @Override
	// protected void onResume()
	// {
	// super.onResume();
	// initilizeMap();
	// }
	
	public class MyAdapter extends FragmentStatePagerAdapter
	{
		BreiefFragment[] theBreiefFragments = new BreiefFragment[4];
		
		public MyAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public int getCount()
		{
			return 4;
		}

		@Override
		public Fragment getItem(int position)
		{
			theBreiefFragments[position] = BreiefFragment.newInstance();
			return theBreiefFragments[position];
		}
		
		public BreiefFragment getCurrBreiefFragment(int position){
			return theBreiefFragments[position];
		}
		
	}

}
