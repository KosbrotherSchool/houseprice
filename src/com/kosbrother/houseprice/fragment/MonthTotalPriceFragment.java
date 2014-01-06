package com.kosbrother.houseprice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kosbrother.houseprice.R;

public class MonthTotalPriceFragment extends Fragment		
{
	public static MonthTotalPriceFragment newInstance()
	{
		MonthTotalPriceFragment f = new MonthTotalPriceFragment();
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_total_price, null);
		return v;
	}
}
