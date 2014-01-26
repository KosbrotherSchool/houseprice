package com.kosbrother.houseprice.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kosbrother.houseprice.entity.RealEstate;

import android.util.Log;

public class HouseApi
{
	final static String HOST = "http://1.34.193.26/";
	public static final String TAG = "HOUSE_API";
	public static final boolean DEBUG = true;

	public static ArrayList<RealEstate> getAroundAllByAreas(double km_dis, double center_x,
			double center_y)
	{

		String message = getMessageFromServer("GET", "/api/v1/estate/get_estate_by_distance?"
				+ "km_dis=" + km_dis + "&center_x=" + center_x + "&center_y=" + center_y, null,
				null);

		ArrayList<RealEstate> realEstates = new ArrayList<RealEstate>();
		if (message == null)
		{
			return null;
		} else
		{
			return parseEstateMessage(message, realEstates);
		}
	}

	private static ArrayList<RealEstate> parseEstateMessage(String message,
			ArrayList<RealEstate> realEstates)
	{

		Log.i(TAG, "Start Parse Json:" + System.currentTimeMillis());
		try
		{

			JSONArray jArray;
			jArray = new JSONArray(message.toString());
			for (int i = 0; i < jArray.length(); i++)
			{

				int id = 0;
				try
				{
					id = jArray.getJSONObject(i).getInt("id");
				} catch (Exception e)
				{
					// TODO: handle exception
				}

				int exchange_year = 0;
				try
				{
					exchange_year = jArray.getJSONObject(i).getInt("exchange_year");

				} catch (Exception e)
				{
					// TODO: handle exception
				}

				int exchange_month = 0;
				try
				{
					exchange_month = jArray.getJSONObject(i).getInt("exchange_month");

				} catch (Exception e)
				{
					// TODO: handle exception
				}

				int total_price = 0;
				try
				{
					total_price = jArray.getJSONObject(i).getInt("total_price");

				} catch (Exception e)
				{
					// TODO: handle exception
				}

				double square_price = 0;
				try
				{
					square_price = jArray.getJSONObject(i).getDouble("square_price");

				} catch (Exception e)
				{
					// TODO: handle exception
				}

				int building_type_id = 0;
				try
				{
					building_type_id = jArray.getJSONObject(i).getInt("building_type_id");

				} catch (Exception e)
				{
					// TODO: handle exception
				}

				int ground_type_id = 0;
				try
				{
					ground_type_id = jArray.getJSONObject(i).getInt("ground_type_id");

				} catch (Exception e)
				{
					// TODO: handle exception
				}

//				String estate_address = "";
//				try
//				{
//					estate_address = jArray.getJSONObject(i).getString("address");
//				} catch (Exception e)
//				{
//					// TODO: handle exception
//				}

				double x_lat = jArray.getJSONObject(i).getDouble("x_long");
				double y_long = jArray.getJSONObject(i).getDouble("y_lat");

				RealEstate newEstate = new RealEstate(id, exchange_year, exchange_month,
						total_price, square_price, x_lat, y_long, building_type_id, ground_type_id);
				realEstates.add(newEstate);

			}

		} catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}

		Log.i(TAG, "End Parse Json:" + System.currentTimeMillis());
		return realEstates;

	}

	public static String getMessageFromServer(String requestMethod, String apiPath,
			JSONObject json, String apiUrl)
	{
		Log.i(TAG, "Start Load from server:" + System.currentTimeMillis());
		URL url;
		try
		{
			if (apiUrl != null)
				url = new URL(apiUrl);
			else
				url = new URL(HOST + apiPath);

			if (DEBUG)
				Log.d(TAG, "URL: " + url);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(requestMethod);

			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			if (requestMethod.equalsIgnoreCase("POST"))
				connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();

			if (requestMethod.equalsIgnoreCase("POST"))
			{
				OutputStream outputStream;

				outputStream = connection.getOutputStream();
				if (DEBUG)
					Log.d("post message", json.toString());

				outputStream.write(json.toString().getBytes());
				outputStream.flush();
				outputStream.close();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuilder lines = new StringBuilder();
			;
			String tempStr;

			while ((tempStr = reader.readLine()) != null)
			{
				lines = lines.append(tempStr);
			}
			if (DEBUG)
				Log.d("MOVIE_API", lines.toString());

			reader.close();
			connection.disconnect();

			Log.i(TAG, "End Load from server:" + System.currentTimeMillis());

			return lines.toString();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
