package com.kosbrother.houseprice;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
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
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosbrother.houseprice.api.HouseApi;
import com.kosbrother.houseprice.entity.RealEstate;
import com.kosbrother.houseprice.fragment.BreiefFragment;
import com.kosbrother.houseprice.fragment.MonthSquarePriceFragment;
import com.kosbrother.houseprice.fragment.MonthTotalPriceFragment;
import com.kosbrother.houseprice.fragment.TransparentSupportMapFragment;

public class MainActivity extends SherlockFragmentActivity implements
		LocationListener, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener
{

	private GoogleMap mGoogleMap;
	private FragmentTabHost mTabHost;
	// private TabManager mTabManager;

	private LinearLayout mLayoutDataChange;
	private MyAdapter mAdapter;
	private ViewPager mPager;

	private ImageButton btnList;
	private ImageButton btnPrevious;
	private ImageButton btnNext;
	private TextView textYearMonth;
	private Button btnDistance;
	private EditText editTextSearch;
	private ImageView imageViewSearch;

	private double km_dis;
	private double center_x;
	private double center_y;

	private LocationClient mLocationClient;

	private int crawlDateNum = 10211;

	public static boolean isReSearch = true;
	public static String hpMinString;
	public static String hpMaxString;
	public static String areaMinString;
	public static String areaMaxString;
	public static String groundTypeString;
	public static String buildingTypeString;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		km_dis = Double
				.valueOf(Setting.getSetting(Setting.keyKmDistance, this));
		hpMinString = Setting.getSetting(Setting.keyHousePriceMin,
				MainActivity.this);
		if (hpMinString.equals("0"))
		{
			hpMinString = null;
		}
		hpMaxString = Setting.getSetting(Setting.keyHousePriceMax,
				MainActivity.this);
		if (hpMaxString.equals("0"))
		{
			hpMaxString = null;
		}
		areaMinString = Setting.getSetting(Setting.keyAreaMin,
				MainActivity.this);
		if (areaMinString.equals("0"))
		{
			areaMinString = null;
		}
		areaMaxString = Setting.getSetting(Setting.keyAreaMax,
				MainActivity.this);
		if (areaMaxString.equals("0"))
		{
			areaMaxString = null;
		}
		groundTypeString = Setting.getSetting(Setting.keyGroundType,
				MainActivity.this);
		if (groundTypeString.equals("0"))
		{
			groundTypeString = null;
		}
		buildingTypeString = Setting.getSetting(Setting.keyBuildingType,
				MainActivity.this);
		if (buildingTypeString.equals("0"))
		{
			buildingTypeString = null;
		}

		new GetCurrentDateTask().execute();

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
					MonthSquarePriceFragment theMSP = (MonthSquarePriceFragment) getSupportFragmentManager()
							.findFragmentByTag("simple");
					theMSP.setDatas();
					mTabHost.setVisibility(View.VISIBLE);
				}
			}
		});

		btnList = (ImageButton) findViewById(R.id.button_list);
		btnList.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				BreiefFragment theBreiefFragment = mAdapter
						.getCurrBreiefFragment(mPager.getCurrentItem());
				if (theBreiefFragment.changeToDetailView())
				{
					theBreiefFragment.addDetailViews();
				}

			}
		});

		btnDistance = (Button) findViewById(R.id.button_distance);
		btnDistance.setText(Double.toString(km_dis) + "km");
		btnDistance.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showSelectDistanceDialog();
			}
		});

		textYearMonth = (TextView) findViewById(R.id.text_year_month);
		editTextSearch = (EditText) findViewById(R.id.edittext_search);
		editTextSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		editTextSearch.setInputType(InputType.TYPE_CLASS_TEXT);
		editTextSearch.requestFocus();
		editTextSearch
				.setOnEditorActionListener(new TextView.OnEditorActionListener()
				{

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event)
					{
						if (actionId == EditorInfo.IME_ACTION_SEARCH
								|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
						{
							InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							inputMethodManager.hideSoftInputFromWindow(
									getCurrentFocus().getWindowToken(), 0);
							searchLocation();
							return true;
						}
						return false;
					}
				});

		imageViewSearch = (ImageView) findViewById(R.id.imageview_search);

		imageViewSearch.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (editTextSearch.getText().toString().equals(""))
				{
					Toast.makeText(MainActivity.this, "請輸入搜索地址",
							Toast.LENGTH_SHORT).show();
				} else
				{
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							getCurrentFocus().getWindowToken(), 0);
					searchLocation();
				}

			}
		});

		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				try
				{
					String dataString = Datas.mArrayKey.get(position)
							.substring(0, 3)
							+ "/"
							+ Datas.mArrayKey.get(position).substring(3);
					textYearMonth.setText(dataString);

					setMapMark(mPager.getCurrentItem());
					BreiefFragment theBreiefFragment = mAdapter
							.getCurrBreiefFragment(mPager.getCurrentItem());
					theBreiefFragment.setBriefViews();
				} catch (Exception e)
				{
					// TODO: handle exception
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});

		btnPrevious = (ImageButton) findViewById(R.id.button_previous);
		btnPrevious.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mPager.getCurrentItem() == 0)
				{
					Toast.makeText(MainActivity.this, "無上頁", Toast.LENGTH_SHORT)
							.show();
				} else
				{
					mPager.setCurrentItem(mPager.getCurrentItem() - 1);
					setMapMark(mPager.getCurrentItem());
				}
			}
		});

		btnNext = (ImageButton) findViewById(R.id.button_next);
		btnNext.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (mPager.getCurrentItem() == 3)
				{
					Toast.makeText(MainActivity.this, "無下頁", Toast.LENGTH_SHORT)
							.show();
				} else
				{
					mPager.setCurrentItem(mPager.getCurrentItem() + 1);
					setMapMark(mPager.getCurrentItem());
				}

			}
		});

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("單價"),
				MonthSquarePriceFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("總價"),
				MonthTotalPriceFragment.class, null);

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

	private void searchLocation()
	{
		String inputString = editTextSearch.getText().toString();

		Geocoder geocoder = new Geocoder(MainActivity.this);
		List<Address> addresses = null;
		Address address = null;
		try
		{
			addresses = geocoder.getFromLocationName(inputString, 1);
		} catch (Exception e)
		{
			Log.e("MainActivity", e.toString());
		}
		if (addresses == null || addresses.isEmpty())
		{
			Toast.makeText(MainActivity.this, "無此地點", Toast.LENGTH_SHORT)
					.show();
		} else
		{
			address = addresses.get(0);
			double geoLat = address.getLatitude();
			double geoLong = address.getLongitude();
			AppConstants.currentLatLng = new LatLng(geoLat, geoLong);

			float mapSize = 15.0f;

			if (0 < km_dis && km_dis <= 0.3)
			{
				mapSize = 16.0f;
			} else if (0.3 < km_dis && km_dis <= 0.5)
			{
				mapSize = 15.0f;
			} else if (0.5 < km_dis && km_dis <= 1)
			{
				mapSize = 14.0f;
			} else if (1 < km_dis && km_dis <= 2)
			{
				mapSize = 13.0f;
			} else
			{
				mapSize = 12.0f;
			}

			mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(AppConstants.currentLatLng.latitude,
							AppConstants.currentLatLng.longitude), mapSize));
			center_x = geoLong;
			center_y = geoLat;
			new GetEstatesTask().execute();

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

			mGoogleMap.setMyLocationEnabled(true);

			mGoogleMap
					.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener()
					{

						@Override
						public boolean onMyLocationButtonClick()
						{
							// TODO Auto-generated method stub
							getLocation();
							return false;
						}
					});

			// check if map is created successfully or not
			if (mGoogleMap == null)
			{
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item)
	{

		switch (item.getItemId())
		{
		case R.id.menu_filter:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, FilterActivity.class);
			startActivity(intent);
			break;
		// case R.id.menu_query_record:
		// Toast.makeText(MainActivity.this, "list",
		// Toast.LENGTH_SHORT).show();
		// Intent intent = new Intent();
		// intent.setClass(MainActivity.this, ListActivity.class);
		// // intent.putExtras(bundle);
		// startActivity(intent);
		// break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// initilizeMap();
		if (isReSearch && mLocationClient.isConnected())
		{
			getLocation();
			isReSearch = false;
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
			isReSearch = false;
		}

	}

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
			theBreiefFragments[position] = BreiefFragment.newInstance(position);
			return theBreiefFragments[position];
		}

		public BreiefFragment getCurrBreiefFragment(int position)
		{
			return theBreiefFragments[position];
		}

	}

	private class GetCurrentDateTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... arg0)
		{
			// TODO Auto-generated method stub
			crawlDateNum = HouseApi.getCurrentCrawlDate();
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			// Toast.makeText(MainActivity.this, Integer.toString(crawlDateNum),
			// Toast.LENGTH_SHORT).show();
			makeArrayKey(crawlDateNum);
		}

		private void makeArrayKey(int crawlDateNum)
		{
			int year = crawlDateNum / 100;
			int month = crawlDateNum % 100;
			Toast.makeText(MainActivity.this,
					Integer.toString(year) + "/" + Integer.toString(month),
					Toast.LENGTH_SHORT).show();

			if (month > 4)
			{
				for (int i = 0; i < 4; i++)
				{
					int monthKey = month - i;
					Datas.mArrayKey
							.add(Integer.toString(year * 100 + monthKey));
				}
			} else
			{
				for (int i = 0; i < 4; i++)
				{
					int monthKey = (month + 12 - i) % 12;
					if (monthKey == 0)
					{
						monthKey = 12;
					}
					Datas.mArrayKey
							.add(Integer.toString(year * 100 + monthKey));
				}
			}

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

			Datas.mEstates = HouseApi.getAroundAllByAreas(km_dis, center_x,
					center_y, hpMinString, hpMaxString, areaMinString,
					areaMaxString, groundTypeString, buildingTypeString);

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{

			if (Datas.mEstates != null && Datas.mEstates.size() != 0)
			{

				Datas.mEstatesMap = getRealEstatesMap(Datas.mEstates);

				setMapMark(mPager.getCurrentItem());
				BreiefFragment theBreiefFragment = mAdapter
						.getCurrBreiefFragment(mPager.getCurrentItem());
				theBreiefFragment.setBriefViews();

			} else
			{
				Toast.makeText(MainActivity.this, "No Data!!",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	private void setMapMark(int pagerPosition)
	{
		mGoogleMap.clear();
		String monthKey = Datas.mArrayKey.get(pagerPosition);
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);

		if (theEstates != null)
		{
			for (int i = 0; i < theEstates.size(); i++)
			{
				LatLng newLatLng = new LatLng(theEstates.get(i).y_lat,
						theEstates.get(i).x_long);

				MarkerOptions marker = new MarkerOptions().position(newLatLng)
						.title(Integer.toString(i));
				marker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_sale));
				mGoogleMap.addMarker(marker);

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
				AppConstants.currentLatLng = new LatLng(
						currentLocation.getLatitude(),
						currentLocation.getLongitude());
			} else
			{

				AppConstants.currentLatLng = new LatLng(25.0478, 121.5172);

			}

			center_x = AppConstants.currentLatLng.longitude;
			center_y = AppConstants.currentLatLng.latitude;

			float mapSize = 15.0f;

			if (0 < km_dis && km_dis <= 0.3)
			{
				mapSize = 16.0f;
			} else if (0.3 < km_dis && km_dis <= 0.5)
			{
				mapSize = 15.0f;
			} else if (0.5 < km_dis && km_dis <= 1)
			{
				mapSize = 14.0f;
			} else if (1 < km_dis && km_dis <= 2)
			{
				mapSize = 13.0f;
			} else
			{
				mapSize = 12.0f;
			}

			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					AppConstants.currentLatLng.latitude,
					AppConstants.currentLatLng.longitude), mapSize));
			
			mGoogleMap.setOnMarkerClickListener( new OnMarkerClickListener()
			{
				
				@Override
				public boolean onMarkerClick(Marker marker)
				{
					Intent intent = new Intent();
					String monthKey = Datas.mArrayKey.get(mPager.getCurrentItem());
					intent.putExtra("MonthKey", monthKey);
					intent.putExtra("RowNumber", Integer.valueOf(marker.getTitle()));
					intent.setClass(MainActivity.this, DetailActivity.class);
					startActivity(intent);
					return true;
				}
			});
			
			
			// Taipei Train Station
			// center_x = 121.5172;
			// center_y = 25.0478;

			// mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
			// LatLng(center_y, center_x),
			// 16.0f));

			new GetEstatesTask().execute();

		}
	}

	@Override
	public void onConnected(Bundle bundle)
	{

		// if (Datas.mEstates == null || Datas.mEstates.size() == 0)
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
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

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
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			if (dialog != null)
			{
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(),
						LocationUtils.APPTAG);
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
		isReSearch = true;
		
		super.onStop();
	}

	private void stopPeriodicUpdates()
	{
		mLocationClient.removeLocationUpdates(this);
		// mConnectionState.setText(R.string.location_updates_stopped);
	}

	private TreeMap<String, ArrayList<RealEstate>> getRealEstatesMap(
			ArrayList<RealEstate> realEstates)
	{

		TreeMap<String, ArrayList<RealEstate>> estateMap = new TreeMap<String, ArrayList<RealEstate>>();
		for (int i = 0; i < realEstates.size(); i++)
		{
			RealEstate realEstate = realEstates.get(i);
			String realEstateKey = Integer.toString(realEstate.exchange_year
					* 100 + realEstate.exchange_month);
			// 先確認key是否存在
			if (estateMap.containsKey(realEstateKey))
			{
				// 已經有的話就把movie加進去
				((ArrayList<RealEstate>) estateMap.get(realEstateKey))
						.add(realEstate);
			} else
			{
				// 沒有的話就建一個加進去
				ArrayList<RealEstate> newRealEstateList = new ArrayList<RealEstate>(
						10);
				newRealEstateList.add(realEstate);
				estateMap.put(realEstateKey, newRealEstateList);
			}
		}
		return estateMap;
	}

	private void showSelectDistanceDialog()
	{

		AlertDialog.Builder editDialog = new AlertDialog.Builder(
				MainActivity.this);
		editDialog.setTitle("選取搜索範圍");

		// final EditText editText = new EditText(ArticleActivity.this);
		// editDialog.setView(editText);

		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View distance_view = inflater.inflate(R.layout.dialog_select_distance,
				null);
		final TextView textDistance = (TextView) distance_view
				.findViewById(R.id.text_distance);
		SeekBar seekBarDistance = (SeekBar) distance_view
				.findViewById(R.id.seekbar_distance);

		textDistance.setText(Double.toString(km_dis) + "km");
		int pp = (int) (km_dis / 0.03);
		seekBarDistance.setProgress(pp);

		seekBarDistance
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
				{

					@Override
					public void onStopTrackingTouch(SeekBar seekBar)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser)
					{
						// TODO Auto-generated method stub
						double d = progress * 0.03;
						String d_String = Double.toString(d).substring(0, 3);

						textDistance.setText(d_String + "km");
						km_dis = Double.valueOf(d_String);
					}
				});

		editDialog.setView(distance_view);

		editDialog.setPositiveButton("確定",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
						if (km_dis != 0)
						{
							btnDistance.setText(Double.toString(km_dis) + "km");
							Setting.saveSetting(Setting.keyKmDistance,
									Double.toString(km_dis), MainActivity.this);
							getLocation();
						} else
						{
							Toast.makeText(MainActivity.this, "半徑不能為0",
									Toast.LENGTH_SHORT).show();
						}

					}
				});
		editDialog.setNegativeButton("取消",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
					}
				});
		editDialog.show();
	}

}
