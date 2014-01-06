package com.kosbrother.houseprice.fragment;

import com.kosbrother.houseprice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MonthSquarePriceFragment extends Fragment		
{
	public static MonthSquarePriceFragment newInstance()
	{
		MonthSquarePriceFragment f = new MonthSquarePriceFragment();
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_square_price, null);
		return v;
	}
}
