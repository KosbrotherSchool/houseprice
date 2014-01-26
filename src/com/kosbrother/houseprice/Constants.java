package com.kosbrother.houseprice;

import com.google.android.gms.maps.model.LatLng;

public class Constants
{
	public static boolean isSaledMarkerShow = true;
	public static boolean isRentMarkerShow = true;
	public static boolean isPreSaleMarkerShow = true;
	public static int salePerSquareMin = 0;
	// if is 0, means no need to add query string
	public static int salePerSquareMax = 0;
	public static int saleTotalMin = 0;
	// if is 0, means no need to add query string
	public static int saleTotalMax = 0;
	public static double saleAreaMin = 0;
	// if is 0, means no need to add query string
	public static double saleAreaMax = 0;
	public static LatLng currentLatLng;
	
	public static final String MEDIATION_KEY = "3fa92baa5f514278";
	
}
