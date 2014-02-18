package com.kosbrother.houseprice;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;

public class Setting
{
	public final static String keyPref = "Preference";

	public final static String keyPurpose = "Purpose";
	public final static String keyHousePriceMin = "HP_Min";
	public final static String keyHousePriceMax = "HP_Max";
	public final static String keyGroundType = "GroundType";
	public final static String keyBuildingType = "BuildingType";
	public final static String keyAreaMin = "Area_Min";
	public final static String keyAreaMax = "Area_Max";
	public final static String keyKmDistance = "Km_Distance";
	public final static String keyFirstOpen = "First_Open";
	
	public final static String initialPurpose = "0"; // 0 for buy, 1 for sell
	public final static String initialHousePriceMin = "0"; 
	public final static String initialHousePriceMax = "0"; //0 for max
	public final static String initialGroundType = "0";
	public final static String initialBuildingType = "0";
	public final static String initialAreaMin = "0";
	public final static String initialAreaMax = "0"; //0 for max
	public final static String initialKmDistance = "0.5";
	public final static boolean initialFirstOpen = true;
	
	private static final HashMap<String, String> initMap = new HashMap<String, String>()
	{
		{
			put(keyPurpose, initialPurpose);
			put(keyHousePriceMin, initialHousePriceMin);
			put(keyHousePriceMax, initialHousePriceMax);
			put(keyGroundType, initialGroundType);
			put(keyBuildingType, initialBuildingType);
			put(keyAreaMin, initialAreaMin);
			put(keyAreaMax, initialAreaMax);
			put(keyKmDistance, initialKmDistance);
		}
	};

	
	public static String getSetting(String settingKey, Context context) {
        SharedPreferences sharePreference = context.getSharedPreferences(keyPref, 0);
        String settingValue = sharePreference.getString(settingKey, initMap.get(settingKey));
        return settingValue;
    }

	public static void saveSetting(String settingKey, String settingValue, Context context) {
        SharedPreferences sharePreference = context.getSharedPreferences(keyPref, 0);
        sharePreference.edit().putString(settingKey, settingValue).commit();
    }
	
	
	public static boolean getFirstBoolean(Context context){
		 SharedPreferences sharePreference = context.getSharedPreferences(keyPref, 0);
		 boolean firstBoolean = sharePreference.getBoolean(keyFirstOpen, initialFirstOpen);
		 return firstBoolean;
	}
	
	public static void setFirstBoolean(Context context){
		SharedPreferences sharePreference = context.getSharedPreferences(keyPref, 0);
		sharePreference.edit().putBoolean(keyFirstOpen, false).commit();
	}
}
