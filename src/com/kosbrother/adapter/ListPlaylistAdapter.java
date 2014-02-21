package com.kosbrother.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosbrother.houseprice.R;
import com.kosbrother.houseprice.entity.RealEstate;
import com.kosbrother.imageloader.ImageLoader;


public class ListPlaylistAdapter extends BaseAdapter {

    private final Activity        activity;
    private final ArrayList<RealEstate> data;
    private static LayoutInflater inflater = null;


    public ListPlaylistAdapter(Activity a, ArrayList<RealEstate> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
        	vi = inflater.inflate(R.layout.item_detail_row, null);
               
	        TextView timeTextView = (TextView) vi.findViewById(R.id.time_text);
	        TextView exchangeTypeTextView = (TextView) vi.findViewById(R.id.exchange_type_text);
	        TextView buildingTyppTextView = (TextView) vi.findViewById(R.id.building_type_text);
	        TextView totalPriceTextView = (TextView) vi.findViewById(R.id.total_price_text);
	        TextView squarePriceTextView = (TextView) vi.findViewById(R.id.square_price_text);
	        TextView totalAreaTextView = (TextView) vi.findViewById(R.id.total_area_text);
	        
	        
	
	        vi.setClickable(true);
	        vi.setFocusable(true);
	        vi.setBackgroundResource(android.R.drawable.menuitem_background);
	        vi.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	
//	            	TextView idView = (TextView) v.findViewById(R.id.text_playlist_id);
//					String id = idView.getText().toString();			
//					TextView titleView = (TextView) v.findViewById(R.id.text_playlist_title);
//					String title = titleView.getText().toString();
//	            	
//	            	Intent intent = new Intent(activity, PlaylistVideosActivity.class);
//	 				Bundle bundle = new Bundle();
//	 				bundle.putString("ListId", id); 
//	 				bundle.putString("ListTitle", title);
//	 				bundle.putString("ChannelTitle", mChannelTitle);
//	 				intent.putExtras(bundle);
//	 				activity.startActivity(intent); 
	                
	            }
	        });
        
        
        return vi;
    }
}
