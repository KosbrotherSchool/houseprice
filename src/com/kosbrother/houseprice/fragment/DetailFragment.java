package com.kosbrother.houseprice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kosbrother.houseprice.R;

public class DetailFragment extends Fragment
{		
	
	TableLayout detailTableLayout;
	
	public static DetailFragment newInstance()
	{
		DetailFragment f = new DetailFragment();
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_detail, null);
		detailTableLayout = (TableLayout) v.findViewById(R.id.detail_talble);
		
		for(int i=0; i<30 ; i++){
			TableRow newTableRow = new TableRow(getActivity());
			
			TextView tDateView = new TextView(getActivity());
			tDateView.setText("102/1");
			newTableRow.addView(tDateView);
			
			TextView tBuyTypeView = new TextView(getActivity());
			tBuyTypeView.setText("房地");
			newTableRow.addView(tBuyTypeView);
			
			TextView tBuildingView = new TextView(getActivity());
			tBuildingView.setText("公寓");
			newTableRow.addView(tBuildingView);
			
			TextView tTotalPriceView = new TextView(getActivity());
			tTotalPriceView.setText("2020");
			newTableRow.addView(tTotalPriceView);
			
			TextView tSquarePriceView = new TextView(getActivity());
			tSquarePriceView.setText("12.3");
			newTableRow.addView(tSquarePriceView);
			
			TextView tRoomsView = new TextView(getActivity());
			tRoomsView.setText("3/2/1");
			newTableRow.addView(tRoomsView);
			
			detailTableLayout.addView(newTableRow);
			
		}
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

	}

}
