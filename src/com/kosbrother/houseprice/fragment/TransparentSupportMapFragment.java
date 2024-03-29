package com.kosbrother.houseprice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

public class TransparentSupportMapFragment extends SupportMapFragment
{	
	
	
	public View mMapFragment;
//	public TouchableWrapper mTouchView;   
	
	public TransparentSupportMapFragment()
	{

		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstance)
	{

		View layout = super.onCreateView(inflater, view, savedInstance);
		FrameLayout frameLayout = new FrameLayout(getActivity());
		frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		((ViewGroup) layout).addView(frameLayout, new ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return layout;
		
//		mMapFragment = super.onCreateView(inflater, view, savedInstance);    
//	    mTouchView = new TouchableWrapper(getActivity());
//	    mTouchView.addView(mMapFragment);
//	    return mTouchView;
	}

	@Override
	public View getView()
	{
		return mMapFragment;
	}
	
//	public boolean isMpaTouched()
//	{
//		return mTouchView.getIsMapTouched();
//	}
	
}