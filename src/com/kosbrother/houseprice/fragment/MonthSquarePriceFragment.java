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

public class MonthSquarePriceFragment extends Fragment
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

	private TextView textPriceChange1;
	private TextView textPriceChange2;
	private TextView textPriceChange3;
	private TextView textPriceChange4;

	private TextView textLowPrice1;
	private TextView textLowPrice2;
	private TextView textLowPrice3;
	private TextView textLowPrice4;

	private TextView textItemNums1;
	private TextView textItemNums2;
	private TextView textItemNums3;
	private TextView textItemNums4;

	public static MonthSquarePriceFragment newInstance()
	{
		MonthSquarePriceFragment f = new MonthSquarePriceFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_square_price, null);

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

		textPriceChange1 = (TextView) v.findViewById(R.id.text_price_change_1);
		textPriceChange2 = (TextView) v.findViewById(R.id.text_price_change_2);
		textPriceChange3 = (TextView) v.findViewById(R.id.text_price_change_3);
		textPriceChange4 = (TextView) v.findViewById(R.id.text_price_change_4);

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

	public void setDatas()
	{

		String monthKey1 = Datas.mArrayKey.get(0);
		String monthKey2 = Datas.mArrayKey.get(1);
		String monthKey3 = Datas.mArrayKey.get(2);
		String monthKey4 = Datas.mArrayKey.get(3);

		textMonth1.setText(InfoParserApi.parseMonthKey(monthKey1));
		textMonth2.setText(InfoParserApi.parseMonthKey(monthKey2));
		textMonth3.setText(InfoParserApi.parseMonthKey(monthKey3));
		textMonth4.setText(InfoParserApi.parseMonthKey(monthKey4));

		double hp1 = Datas.getMonthHighSquarePrice(monthKey1);
		textHighPrice1.setText(InfoParserApi.parseSquarePrice(hp1));
		double hp2 = Datas.getMonthHighSquarePrice(monthKey2);
		textHighPrice2.setText(InfoParserApi.parseSquarePrice(hp2));
		double hp3 = Datas.getMonthHighSquarePrice(monthKey3);
		textHighPrice3.setText(InfoParserApi.parseSquarePrice(hp3));
		double hp4 = Datas.getMonthHighSquarePrice(monthKey4);
		textHighPrice4.setText(InfoParserApi.parseSquarePrice(hp4));

		double avg1 = Datas.getMonthAvgSquarePrice(monthKey1);
		textAvgPrice1.setText(InfoParserApi.parseSquarePrice(avg1));
		double avg2 = Datas.getMonthAvgSquarePrice(monthKey2);
		textAvgPrice2.setText(InfoParserApi.parseSquarePrice(avg2));
		double avg3 = Datas.getMonthAvgSquarePrice(monthKey3);
		textAvgPrice3.setText(InfoParserApi.parseSquarePrice(avg3));
		double avg4 = Datas.getMonthAvgSquarePrice(monthKey4);
		textAvgPrice4.setText(InfoParserApi.parseSquarePrice(avg4));

		double priceChange1 = Datas.getSquarePriceChange(monthKey1, monthKey2);
		textPriceChange1.setText(InfoParserApi.parsePriceChangePercent(priceChange1));
		double priceChange2 = Datas.getSquarePriceChange(monthKey2, monthKey3);
		textPriceChange2.setText(InfoParserApi.parsePriceChangePercent(priceChange2));
		double priceChange3 = Datas.getSquarePriceChange(monthKey3, monthKey4);
		textPriceChange3.setText(InfoParserApi.parsePriceChangePercent(priceChange3));
		textPriceChange4.setText(InfoParserApi.parsePriceChangePercent(0));

		double lp1 = Datas.getMonthLowSquarePrice(monthKey1);
		textLowPrice1.setText(InfoParserApi.parseSquarePrice(lp1));
		double lp2 = Datas.getMonthLowSquarePrice(monthKey2);
		textLowPrice2.setText(InfoParserApi.parseSquarePrice(lp2));
		double lp3 = Datas.getMonthLowSquarePrice(monthKey3);
		textLowPrice3.setText(InfoParserApi.parseSquarePrice(lp3));
		double lp4 = Datas.getMonthLowSquarePrice(monthKey4);
		textLowPrice4.setText(InfoParserApi.parseSquarePrice(lp4));

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
