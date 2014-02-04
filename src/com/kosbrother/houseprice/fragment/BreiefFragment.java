package com.kosbrother.houseprice.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.kosbrother.houseprice.Datas;
import com.kosbrother.houseprice.R;
import com.kosbrother.houseprice.api.InfoParserApi;
import com.kosbrother.houseprice.entity.RealEstate;

public class BreiefFragment extends Fragment
{

	private TableLayout detailTableLayout;
	private LinearLayout layoutBrief;
	private View layoutDetailView;
	private int mPosition;
	
	private TextView textEstateItemNums;
	private TextView textEstateSquarePrice;
	private TextView textSquarePriceChange;
	
	// private static BreiefFragment mBreiefFragment;

	public static BreiefFragment newInstance(int position)
	{
		BreiefFragment f = new BreiefFragment();
		Bundle args = new Bundle();
		args.putInt("num", position);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// mBreiefFragment = this;
		View v = inflater.inflate(R.layout.fragment_breief, null);
		layoutBrief = (LinearLayout) v.findViewById(R.id.layout_breif);
		layoutDetailView = v.findViewById(R.id.layout_detail);
		
		textEstateItemNums = (TextView) v.findViewById(R.id.text_estate_item_num);
		textEstateSquarePrice = (TextView) v.findViewById(R.id.text_estate_square_price);
		textSquarePriceChange = (TextView) v.findViewById(R.id.text_square_price_change);
		
		detailTableLayout = (TableLayout) v.findViewById(R.id.detail_talble);
		Bundle bundle = getArguments();
		mPosition = bundle.getInt("num");
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

	}

	public void changeDetailView()
	{
		layoutBrief.setVisibility(View.GONE);
		layoutDetailView.setVisibility(View.VISIBLE);
	}

	public void addDetailViews()
	{	
		ArrayList<RealEstate>  theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(Datas.mArrayKey.get(mPosition));
		
		for (int i = 0; i < theEstates.size(); i++)
		{

			TableRow newTableRow = new TableRow(getActivity());
			newTableRow.setGravity(Gravity.CENTER_HORIZONTAL);
			// newTableRow.setWeightSum(7);

			String year = Integer.toString(theEstates.get(i).exchange_year);
			String month = Integer.toString(theEstates.get(i).exchange_month);

			TextView tDateView = new TextView(getActivity());
			tDateView.setText(year + "/" + month);
			tDateView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			tDateView.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
			newTableRow.addView(tDateView);

			int groundTypeId = theEstates.get(i).ground_type_id;
			String groundType = InfoParserApi.parseGroundType(groundTypeId);
			TextView tBuyTypeView = new TextView(getActivity());
			tBuyTypeView.setText(groundType);
			tBuyTypeView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			tBuyTypeView.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
			newTableRow.addView(tBuyTypeView);

			int buildingTypeId = theEstates.get(i).building_type_id;
			String buildType = InfoParserApi.parseBuildingType(buildingTypeId);
			TextView tBuildingView = new TextView(getActivity());
			tBuildingView.setText(buildType);
			tBuildingView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			tBuildingView.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
			newTableRow.addView(tBuildingView);

			TextView tTotalPriceView = new TextView(getActivity());
			tTotalPriceView.setText(Integer.toString(theEstates.get(i).total_price));
			tTotalPriceView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			tTotalPriceView.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
			newTableRow.addView(tTotalPriceView);

			TextView tSquarePriceView = new TextView(getActivity());
			tSquarePriceView.setText(Double.toString(theEstates.get(i).square_price));
			tSquarePriceView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			tSquarePriceView.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
			newTableRow.addView(tSquarePriceView);

			TextView tArea = new TextView(getActivity());
			tArea.setText("22坪");
			tArea.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
			tArea.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
			newTableRow.addView(tArea);

//			TextView tRoomsView = new TextView(getActivity());
//			tRoomsView.setText("3/2/1");
//			tRoomsView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
//			tRoomsView.setLayoutParams(new TableRow.LayoutParams(0, 40, 1f));
//			newTableRow.addView(tRoomsView);

			detailTableLayout.addView(newTableRow);

		}
	}
	
	public void setBriefViews(){
		//	get data, and last month data
//		ArrayList<RealEstate>  theEstates = new ArrayList<RealEstate>();
//		theEstates = Datas.mEstatesMap.get(Datas.mArrayKey.get(mPosition));
		
		String monthKey = Datas.mArrayKey.get(mPosition);
		
		
		int itemNums = Datas.getMonthEstatesNum(monthKey);
		textEstateItemNums.setText(Integer.toString(itemNums) + "筆");
		
		
		double avgSquarePrice = Datas.getMonthAvgSquarePrice(monthKey);
		String avgSquarePriceString = Double.toString(avgSquarePrice);
		if (avgSquarePriceString.indexOf(".")!=-1)
		{
			textEstateSquarePrice.setText(avgSquarePriceString.substring(0, avgSquarePriceString.indexOf(".")+2) + "萬");
		}else {
			textEstateSquarePrice.setText(avgSquarePriceString + "萬");
		}
		
		
		try
		{
			String lastMonthKey = Datas.mArrayKey.get(mPosition + 1);
			double percentChange =  Datas.getSquarePriceChange(monthKey, lastMonthKey);
			if (percentChange > 1){
				percentChange = (percentChange -1)*100;
				String percentString = Double.toString(percentChange);
				if (percentString.indexOf(".")!=-1){
					textSquarePriceChange.setText("漲"+percentString.substring(0, percentString.indexOf(".")+2)+"%");
				}else {
					textSquarePriceChange.setText("漲"+percentString+"%");
				}
			}else {
				percentChange = (1 - percentChange)*100;
				String percentString = Double.toString(percentChange);
				if (percentString.indexOf(".")!=-1){
					textSquarePriceChange.setText("跌"+percentString.substring(0, percentString.indexOf(".")+2)+"%");
				}else {
					textSquarePriceChange.setText("跌"+percentString+"%");
				}
			}
			
			
		} catch (Exception e)
		{
			textSquarePriceChange.setText(" ~ " + "%");
		}
		
		
	}
	
	// public static BreiefFragment getBreiefFragment(){
	// return mBreiefFragment;
	// }

}
