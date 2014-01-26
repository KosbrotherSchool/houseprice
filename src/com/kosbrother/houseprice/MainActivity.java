package com.kosbrother.houseprice;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosbrother.houseprice.api.HouseApi;
import com.kosbrother.houseprice.fragment.BreiefFragment;
import com.kosbrother.houseprice.fragment.MonthSquarePriceFragment;
import com.kosbrother.houseprice.fragment.MonthTotalPriceFragment;
import com.kosbrother.houseprice.fragment.TransparentSupportMapFragment;
import com.kosbrother.houseprice.utils.TabManager;

public class MainActivity extends SherlockFragmentActivity implements LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener
{

	private GoogleMap mGoogleMap;
	private TabHost mTabHost;
	private TabManager mTabManager;

	private LinearLayout mLayoutDataChange;
	private MyAdapter mAdapter;
	private ViewPager mPager;

	private Button btnList;

	private double km_dis = 0.1;
	private double center_x;
	private double center_y;

	private LocationClient mLocationClient;

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
				if (mTabHost.getVisibility() == View.VISIBLE)
				{
					mTabHost.setVisibility(View.GONE);
				} else
				{
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
				BreiefFragment theBreiefFragment = mAdapter.getCurrBreiefFragment(mPager
						.getCurrentItem());
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

		mLocationClient = new LocationClient(this, this, this);

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
			// Intent intent = new Intent();
			// intent.setClass(MainActivity.this, ListActivity.class);
			// // intent.putExtras(bundle);
			// startActivity(intent);
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

		public BreiefFragment getCurrBreiefFragment(int position)
		{
			return theBreiefFragments[position];
		}

	}

	protected class GetEstatesTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... Void)
		{
			try
			{
				Datas.mEstates.clear();
			} catch (Exception e)
			{
				// TODO: handle exception
			}

			Datas.mEstates = HouseApi.getAroundAllByAreas(km_dis, center_x, center_y);

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			// mGoogleMap.clear();
			if (Datas.mEstates != null && Datas.mEstates.size() != 0)
			{
				for (int i = 0; i < Datas.mEstates.size(); i++)
				{
					LatLng newLatLng = new LatLng(Datas.mEstates.get(i).y_lat,
							Datas.mEstates.get(i).x_long);

					MarkerOptions marker = new MarkerOptions().position(newLatLng);
					marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_sale));
					mGoogleMap.addMarker(marker);

				}
			} else
			{
				Toast.makeText(MainActivity.this, "No Data!!", Toast.LENGTH_SHORT).show();
			}

		}
	}

	private void getLocation()
	{

		// If Google Play Services is available
		if (servicesConnected())
		{
			Location currentLocation = mLocationClient.getLastLocation();
			if (currentLocation != null)
			{
				Constants.currentLatLng = new LatLng(currentLocation.getLatitude(),
						currentLocation.getLongitude());
			} else
			{

				Constants.currentLatLng = new LatLng(25.0478, 121.5172);

			}
			
			center_x = Constants.currentLatLng.longitude;
			center_y = Constants.currentLatLng.latitude;
			
			
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					Constants.currentLatLng.latitude, Constants.currentLatLng.longitude), 16.0f));

			new GetEstatesTask().execute();

//			mGoogleMap.setOnCameraChangeListener(new OnCameraChangeListener()
//			{
//
//				@Override
//				public void onCameraChange(CameraPosition position)
//				{
//
//				}
//			});

		}
	}

	@Override
	public void onConnected(Bundle bundle)
	{

		if (Datas.mEstates == null || Datas.mEstates.size() == 0)
			getLocation();

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location arg0)
	{
		// TODO Auto-generated method stub

	}

	private boolean servicesConnected()
	{

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode)
		{
			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG, "Google Play Service Available");

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else
		{
			// Display an error dialog
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
			if (dialog != null)
			{
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
			}
			return false;
		}
	}

	public static class ErrorDialogFragment extends DialogFragment
	{

		// Global field to contain the error dialog
		private Dialog mDialog;

		/**
		 * Default constructor. Sets the dialog field to null
		 */
		public ErrorDialogFragment()
		{
			super();
			mDialog = null;
		}

		/**
		 * Set the dialog to display
		 * 
		 * @param dialog
		 *            An error dialog
		 */
		public void setDialog(Dialog dialog)
		{
			mDialog = dialog;
		}

		/*
		 * This method must return a Dialog to the DialogFragment.
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			return mDialog;
		}
	}

	@Override
	public void onStart()
	{

		super.onStart();

		/*
		 * Connect the client. Don't re-start any requests here; instead, wait
		 * for onResume()
		 */
		if (!mLocationClient.isConnected())
		{
			mLocationClient.connect();
		}

	}

	@Override
	public void onStop()
	{

		// // If the client is connected
		if (mLocationClient.isConnected())
		{
			stopPeriodicUpdates();
		}
		//
		// // After disconnect() is called, the client is considered "dead".
		mLocationClient.disconnect();

		super.onStop();
	}

	private void stopPeriodicUpdates()
	{
		mLocationClient.removeLocationUpdates(this);
		// mConnectionState.setText(R.string.location_updates_stopped);
	}
}
