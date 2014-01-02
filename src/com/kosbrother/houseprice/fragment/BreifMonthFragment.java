package com.kosbrother.houseprice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kosbrother.houseprice.R;

public class BreifMonthFragment extends Fragment
{

	private MyAdapter mAdapter;
	private ViewPager mPager;

	public static BreifMonthFragment newInstance()
	{
		BreifMonthFragment f = new BreifMonthFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_month_breif_pager, null);

		mAdapter = new MyAdapter(this.getChildFragmentManager());
		mPager = (ViewPager) v.findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

	}

	public class MyAdapter extends FragmentStatePagerAdapter
	{
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
			return BreiefFragment.newInstance();
		}
	}

}
