package com.kosbrother.houseprice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kosbrother.houseprice.Datas;
import com.kosbrother.houseprice.R;
import com.kosbrother.houseprice.api.InfoParserApi;

public class MonthTotalPriceFragment extends Fragment		
{
	
	private TextView textMonth1;
	private TextView textMonth2;
	private TextView textMonth3;
	private TextView textMonth4;

	private TextView textHighPrice1;
	private TextView textHighPrice2;
	private TextView textHighPrice3;
	private TextView textHighPrice4;

	private TextView textAvgPrice1;
	private TextView textAvgPrice2;
	private TextView textAvgPrice3;
	private TextView textAvgPrice4;

	private TextView textLowPrice1;
	private TextView textLowPrice2;
	private TextView textLowPrice3;
	private TextView textLowPrice4;

	private TextView textItemNums1;
	private TextView textItemNums2;
	private TextView textItemNums3;
	private TextView textItemNums4;
	
	
	
	public static MonthTotalPriceFragment newInstance()
	{
		MonthTotalPriceFragment f = new MonthTotalPriceFragment();
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_total_price, null);
		
		textMonth1 = (TextView) v.findViewById(R.id.text_month_1);
		textMonth2 = (TextView) v.findViewById(R.id.text_month_2);
		textMonth3 = (TextView) v.findViewById(R.id.text_month_3);
		textMonth4 = (TextView) v.findViewById(R.id.text_month_4);

		textHighPrice1 = (TextView) v.findViewById(R.id.text_high_price_1);
		textHighPrice2 = (TextView) v.findViewById(R.id.text_high_price_2);
		textHighPrice3 = (TextView) v.findViewById(R.id.text_high_price_3);
		textHighPrice4 = (TextView) v.findViewById(R.id.text_high_price_4);

		textAvgPrice1 = (TextView) v.findViewById(R.id.text_avg_price_1);
		textAvgPrice2 = (TextView) v.findViewById(R.id.text_avg_price_2);
		textAvgPrice3 = (TextView) v.findViewById(R.id.text_avg_price_3);
		textAvgPrice4 = (TextView) v.findViewById(R.id.text_avg_price_4);

		textLowPrice1 = (TextView) v.findViewById(R.id.text_low_price_1);
		textLowPrice2 = (TextView) v.findViewById(R.id.text_low_price_2);
		textLowPrice3 = (TextView) v.findViewById(R.id.text_low_price_3);
		textLowPrice4 = (TextView) v.findViewById(R.id.text_low_price_4);

		textItemNums1 = (TextView) v.findViewById(R.id.text_item_nums_1);
		textItemNums2 = (TextView) v.findViewById(R.id.text_item_nums_2);
		textItemNums3 = (TextView) v.findViewById(R.id.text_item_nums_3);
		textItemNums4 = (TextView) v.findViewById(R.id.text_item_nums_4);

		if (Datas.mEstatesMap != null)
		{
			setDatas();
		}
		
		return v;
	}

	private void setDatas()
	{
		String monthKey1 = Datas.mArrayKey.get(0);
		String monthKey2 = Datas.mArrayKey.get(1);
		String monthKey3 = Datas.mArrayKey.get(2);
		String monthKey4 = Datas.mArrayKey.get(3);

		textMonth1.setText(InfoParserApi.parseMonthKey(monthKey1));
		textMonth2.setText(InfoParserApi.parseMonthKey(monthKey2));
		textMonth3.setText(InfoParserApi.parseMonthKey(monthKey3));
		textMonth4.setText(InfoParserApi.parseMonthKey(monthKey4));
		
		int hp1 = Datas.getMonthHighTotalPrice(monthKey1);
		textHighPrice1.setText(Integer.toString(hp1)+ "萬");
		int hp2 = Datas.getMonthHighTotalPrice(monthKey2);
		textHighPrice2.setText(Integer.toString(hp2)+ "萬");
		int hp3 = Datas.getMonthHighTotalPrice(monthKey3);
		textHighPrice3.setText(Integer.toString(hp3)+ "萬");
		int hp4 = Datas.getMonthHighTotalPrice(monthKey4);
		textHighPrice4.setText(Integer.toString(hp4)+ "萬");
		
		int avg1 = Datas.getMonthAvgTotalPrice(monthKey1);
		textAvgPrice1.setText(Integer.toString(avg1) + "萬");
		int avg2 = Datas.getMonthAvgTotalPrice(monthKey2);
		textAvgPrice2.setText(Integer.toString(avg2) + "萬");
		int avg3 = Datas.getMonthAvgTotalPrice(monthKey3);
		textAvgPrice3.setText(Integer.toString(avg3) + "萬");
		int avg4 = Datas.getMonthAvgTotalPrice(monthKey4);
		textAvgPrice4.setText(Integer.toString(avg4) + "萬");
		
		int lp1 = Datas.getMonthLowTotalPrice(monthKey1);
		textLowPrice1.setText(Integer.toString(lp1)+ "萬");
		int lp2 = Datas.getMonthLowTotalPrice(monthKey2);
		textLowPrice2.setText(Integer.toString(lp2)+ "萬");
		int lp3 = Datas.getMonthLowTotalPrice(monthKey3);
		textLowPrice3.setText(Integer.toString(lp3)+ "萬");
		int lp4 = Datas.getMonthLowTotalPrice(monthKey4);
		textLowPrice4.setText(Integer.toString(lp4)+ "萬");
		
		int num1 = Datas.getMonthEstatesNum(monthKey1);
		textItemNums1.setText(Integer.toString(num1));
		int num2 = Datas.getMonthEstatesNum(monthKey2);
		textItemNums2.setText(Integer.toString(num2));
		int num3 = Datas.getMonthEstatesNum(monthKey3);
		textItemNums3.setText(Integer.toString(num3));
		int num4 = Datas.getMonthEstatesNum(monthKey4);
		textItemNums4.setText(Integer.toString(num4));
		
	}
}
