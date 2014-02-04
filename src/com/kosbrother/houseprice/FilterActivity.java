package com.kosbrother.houseprice;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FilterActivity extends SherlockFragmentActivity
{
	private ActionBar mActionBar;
	private EditText editHousePrice;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		editHousePrice = (EditText) findViewById(R.id.edit_house_price);
		

		mActionBar = getSupportActionBar();
		mActionBar.setTitle("房價報報---搜索設定");

	}

	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard()
	{
		if (getCurrentFocus() != null)
		{
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
//		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(editHousePrice.getWindowToken(), 0);
	}

	/**
	 * Shows the soft keyboard
	 */
	public void showSoftKeyboard(View view)
	{
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
//		hideSoftKeyboard();
	}

}
