package com.kosbrother.houseprice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import com.kosbrother.houseprice.entity.RealEstate;

public class Datas
{

	public static ArrayList<RealEstate> mEstates = new ArrayList<RealEstate>();
	public static TreeMap<String, ArrayList<RealEstate>> mEstatesMap;
	public static ArrayList<String> mArrayKey = new ArrayList<String>();

	public static class SquarePriceComparator implements Comparator<RealEstate>
	{

		private int myOrder;

		public SquarePriceComparator(int order)
		{
			myOrder = order;
		}

		@Override
		public int compare(RealEstate es1, RealEstate es2)
		{
			if (myOrder == 0)
			{
				return Double.compare(es1.square_price, es2.square_price);
			} else
			{
				return -Double.compare(es1.square_price, es2.square_price);
			}
		}
	}

	public static class TotalPriceComparator implements Comparator<RealEstate>
	{

		private int myOrder;

		public TotalPriceComparator(int order)
		{
			myOrder = order;
		}

		@Override
		public int compare(RealEstate es1, RealEstate es2)
		{
			if (myOrder == 0)
			{
				return Double.compare(es1.total_price, es2.total_price);
			} else
			{
				return -Double.compare(es1.total_price, es2.total_price);
			}
		}

	}

	public static int getMonthEstatesNum(String monthKey)
	{
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);
		return theEstates.size();
	}

	public static double getMonthAvgSquarePrice(String monthKey)
	{
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);

		double sumSquarePrice = 0;
		for (int i = 0; i < theEstates.size(); i++)
		{
			sumSquarePrice = sumSquarePrice + theEstates.get(i).square_price;
		}
		double avgSquarePrice = sumSquarePrice / theEstates.size();

		return avgSquarePrice;
	}

	public static double getSquarePriceChange(String monthKey, String LastMonthKey)
	{

		ArrayList<RealEstate> lastEstates = new ArrayList<RealEstate>();
		lastEstates = Datas.mEstatesMap.get(LastMonthKey);
		double sumLastSquarePrice = 0;
		for (int i = 0; i < lastEstates.size(); i++)
		{
			sumLastSquarePrice = sumLastSquarePrice + lastEstates.get(i).square_price;
		}
		double avgLastSquarePrice = sumLastSquarePrice / lastEstates.size();
		double avgSquarePrice = getMonthAvgSquarePrice(monthKey);
		double percentChange = avgSquarePrice / avgLastSquarePrice;

		return percentChange;
	}

	public static double getMonthHighSquarePrice(String monthKey)
	{
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);
		Collections.sort(theEstates, new Datas.SquarePriceComparator(1));
		return theEstates.get(0).square_price;
	}

	public static double getMonthLowSquarePrice(String monthKey)
	{
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);
		Collections.sort(theEstates, new Datas.SquarePriceComparator(0));
		double lp = 0;
		for (int i = 0; i < theEstates.size(); i++)
		{
			lp = theEstates.get(i).square_price;
			if (lp != 0)
			{
				break;
			}
		}
		return lp;
	}
	
	public static int getMonthHighTotalPrice(String monthKey){
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);
		Collections.sort(theEstates, new Datas.TotalPriceComparator(1));
		return theEstates.get(0).total_price;
	}
	
	public static int getMonthLowTotalPrice(String monthKey)
	{
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);
		Collections.sort(theEstates, new Datas.TotalPriceComparator(0));
		int lp = 0;
		for (int i = 0; i < theEstates.size(); i++)
		{
			lp = theEstates.get(i).total_price;
			if (lp != 0)
			{
				break;
			}
		}
		return lp;
	}
	
	public static int  getMonthAvgTotalPrice(String monthKey)
	{
		ArrayList<RealEstate> theEstates = new ArrayList<RealEstate>();
		theEstates = Datas.mEstatesMap.get(monthKey);
		
		int sumSquarePrice = 0;
		for (int i = 0; i < theEstates.size(); i++)
		{
			sumSquarePrice = sumSquarePrice + theEstates.get(i).total_price;
		}
		int avgSquarePrice = sumSquarePrice / theEstates.size();

		return avgSquarePrice;
		
	}
	
}
