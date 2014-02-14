package com.kosbrother.houseprice;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.internal.el;

public class FilterActivity extends SherlockFragmentActivity
{
	private ActionBar mActionBar;

	private String stringPurpose = "";
	private String minHousePriceString = "";
	private String maxHousePriceString = "";
	private String stringGroundType = "";
	private String stringBuildingType = "";
	private String areaMinString = "";
	private String areaMaxString = "";

	private EditText lowHousePriceEditText;
	private EditText highHousePriceEditText;
	
	private RadioButton radioBuy;
	private RadioButton radioSell;

	private CheckBox ground_type_0;
	private CheckBox ground_type_1;
	private CheckBox ground_type_2;
	private CheckBox ground_type_3;
	private CheckBox ground_type_4;
	private CheckBox ground_type_5;

	private CheckBox building_type_0;
	private CheckBox building_type_a;
	private CheckBox building_type_b;
	private CheckBox building_type_c;
	private CheckBox building_type_d;
	private CheckBox building_type_e;
	private CheckBox building_type_f;
	private CheckBox building_type_g;
	private CheckBox building_type_h;
	private CheckBox building_type_i;
	private CheckBox building_type_j;
	private CheckBox building_type_k;
	private CheckBox building_type_l;

	private EditText areaMinEditText;
	private EditText areaMaxEditText;

	private Button buttonSearch;
	private Button buttonSetOften;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		lowHousePriceEditText = (EditText) findViewById(R.id.low_house_price_edit);
		highHousePriceEditText = (EditText) findViewById(R.id.high_house_price_edit);
		buttonSearch = (Button) findViewById(R.id.button_search);
		buttonSetOften = (Button) findViewById(R.id.button_set_oftenuse);

		buttonSearch.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				MainActivity.isReSearch = true;
				// low house price
				if (!lowHousePriceEditText.getText().toString().equals(""))
				{
					MainActivity.hpMinString = lowHousePriceEditText.getText().toString();
				}

				// high house price
				if (!highHousePriceEditText.getText().toString().equals(""))
				{
					MainActivity.hpMaxString = highHousePriceEditText.getText().toString();
				}
				
				
				// ground type
				String valueGroundType = "";
				if (ground_type_0.isChecked())
				{
					valueGroundType = "0";
				} else
				{
					if (ground_type_1.isChecked())
					{
						valueGroundType = valueGroundType + "1";
					}
					if (ground_type_2.isChecked())
					{
						valueGroundType = valueGroundType + "2";
					}
					if (ground_type_3.isChecked())
					{
						valueGroundType = valueGroundType + "3";
					}
					if (ground_type_4.isChecked())
					{
						valueGroundType = valueGroundType + "4";
					}
					if (ground_type_5.isChecked())
					{
						valueGroundType = valueGroundType + "5";
					}
				}

				if (valueGroundType.equals(""))
				{
					Toast.makeText(FilterActivity.this, "請選擇房地形態", Toast.LENGTH_SHORT).show();
				} else
				{
					MainActivity.groundTypeString = valueGroundType;
				}

				// buiding type
				String valueBuildingType = "";
				if (building_type_0.isChecked())
				{
					valueBuildingType = "0";
				} else
				{

					if (building_type_a.isChecked())
					{
						valueBuildingType = valueBuildingType + "a";
					}
					
					if (building_type_b.isChecked())
					{
						valueBuildingType = valueBuildingType + "b";
					}
					
					if (building_type_c.isChecked())
					{
						valueBuildingType = valueBuildingType + "c";
					}
					
					if (building_type_d.isChecked())
					{
						valueBuildingType = valueBuildingType + "d";
					}
					
					if (building_type_e.isChecked())
					{
						valueBuildingType = valueBuildingType + "e";
					}
					
					if (building_type_f.isChecked())
					{
						valueBuildingType = valueBuildingType + "f";
					}
					
					if (building_type_g.isChecked())
					{
						valueBuildingType = valueBuildingType + "g";
					}
					
					if (building_type_h.isChecked())
					{
						valueBuildingType = valueBuildingType + "h";
					}
					
					if (building_type_i.isChecked())
					{
						valueBuildingType = valueBuildingType + "i";
					}
					
					if (building_type_j.isChecked())
					{
						valueBuildingType = valueBuildingType + "j";
					}
					
					if (building_type_k.isChecked())
					{
						valueBuildingType = valueBuildingType + "k";
					}
					
					if (building_type_l.isChecked())
					{
						valueBuildingType = valueBuildingType + "l";
					}
				}
				
				if (valueBuildingType.equals(""))
				{
					Toast.makeText(FilterActivity.this, "請選擇建物形態", Toast.LENGTH_SHORT).show();
				}else {
					MainActivity.buildingTypeString = valueBuildingType;
				}
				
				// min square price
				if (areaMinEditText.getText().toString().equals("")){
					
				}else {
					MainActivity.areaMinString = areaMinEditText.getText().toString();
				}
				
				// max square price
				if (areaMaxEditText.getText().toString().equals(""))
				{
					
				}else {
					MainActivity.areaMaxString = areaMaxEditText.getText().toString();
				}
				finish();
			}
		});

		buttonSetOften.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// purpose
				String valuePurpose = "";
				if (radioBuy.isChecked())
				{
					valuePurpose = "0";
				} else
				{
					valuePurpose = "1";
				}
				Setting.saveSetting(Setting.keyPurpose, valuePurpose, FilterActivity.this);

				// low house price
				if (!lowHousePriceEditText.getText().toString().equals(""))
				{
					Setting.saveSetting(Setting.keyHousePriceMin, lowHousePriceEditText.getText().toString(), FilterActivity.this);
				}

				// high house price
				if (!highHousePriceEditText.getText().toString().equals(""))
				{
					Setting.saveSetting(Setting.keyHousePriceMax, highHousePriceEditText.getText().toString(), FilterActivity.this);
				}
				
				
				// ground type
				String valueGroundType = "";
				if (ground_type_0.isChecked())
				{
					valueGroundType = "0";
				} else
				{
					if (ground_type_1.isChecked())
					{
						valueGroundType = valueGroundType + "1";
					}
					if (ground_type_2.isChecked())
					{
						valueGroundType = valueGroundType + "2";
					}
					if (ground_type_3.isChecked())
					{
						valueGroundType = valueGroundType + "3";
					}
					if (ground_type_4.isChecked())
					{
						valueGroundType = valueGroundType + "4";
					}
					if (ground_type_5.isChecked())
					{
						valueGroundType = valueGroundType + "5";
					}
				}

				if (valueGroundType.equals(""))
				{
					Toast.makeText(FilterActivity.this, "請選擇房地形態", Toast.LENGTH_SHORT).show();
				} else
				{
					Setting.saveSetting(Setting.keyGroundType, valueGroundType, FilterActivity.this);
				}

				// buiding type
				String valueBuildingType = "";
				if (building_type_0.isChecked())
				{
					valueBuildingType = "0";
				} else
				{

					if (building_type_a.isChecked())
					{
						valueBuildingType = valueBuildingType + "a";
					}
					
					if (building_type_b.isChecked())
					{
						valueBuildingType = valueBuildingType + "b";
					}
					
					if (building_type_c.isChecked())
					{
						valueBuildingType = valueBuildingType + "c";
					}
					
					if (building_type_d.isChecked())
					{
						valueBuildingType = valueBuildingType + "d";
					}
					
					if (building_type_e.isChecked())
					{
						valueBuildingType = valueBuildingType + "e";
					}
					
					if (building_type_f.isChecked())
					{
						valueBuildingType = valueBuildingType + "f";
					}
					
					if (building_type_g.isChecked())
					{
						valueBuildingType = valueBuildingType + "g";
					}
					
					if (building_type_h.isChecked())
					{
						valueBuildingType = valueBuildingType + "h";
					}
					
					if (building_type_i.isChecked())
					{
						valueBuildingType = valueBuildingType + "i";
					}
					
					if (building_type_j.isChecked())
					{
						valueBuildingType = valueBuildingType + "j";
					}
					
					if (building_type_k.isChecked())
					{
						valueBuildingType = valueBuildingType + "k";
					}
					
					if (building_type_l.isChecked())
					{
						valueBuildingType = valueBuildingType + "l";
					}
				}
				
				if (valueBuildingType.equals(""))
				{
					Toast.makeText(FilterActivity.this, "請選擇建物形態", Toast.LENGTH_SHORT).show();
				}else {
					Setting.saveSetting(Setting.keyBuildingType, valueBuildingType, FilterActivity.this);
				}
				
				// min square price
				if (areaMinEditText.getText().toString().equals("")){
					
				}else {
					Setting.saveSetting(Setting.keyAreaMin, areaMinEditText.getText().toString(), FilterActivity.this);
				}
				
				// max square price
				if (areaMaxEditText.getText().toString().equals(""))
				{
					
				}else {
					Setting.saveSetting(Setting.keyAreaMax, areaMaxEditText.getText().toString(), FilterActivity.this);
				}
				
			}
		});

		radioBuy = (RadioButton) findViewById(R.id.radio_buy);
		radioSell = (RadioButton) findViewById(R.id.radio_sell);

		ground_type_0 = (CheckBox) findViewById(R.id.ground_type_0);
		ground_type_1 = (CheckBox) findViewById(R.id.ground_type_1);
		ground_type_2 = (CheckBox) findViewById(R.id.ground_type_2);
		ground_type_3 = (CheckBox) findViewById(R.id.ground_type_3);
		ground_type_4 = (CheckBox) findViewById(R.id.ground_type_4);
		ground_type_5 = (CheckBox) findViewById(R.id.ground_type_5);

		building_type_0 = (CheckBox) findViewById(R.id.building_type_0);
		building_type_a = (CheckBox) findViewById(R.id.building_type_a);
		building_type_b = (CheckBox) findViewById(R.id.building_type_b);
		building_type_c = (CheckBox) findViewById(R.id.building_type_c);
		building_type_d = (CheckBox) findViewById(R.id.building_type_d);
		building_type_e = (CheckBox) findViewById(R.id.building_type_e);
		building_type_f = (CheckBox) findViewById(R.id.building_type_f);
		building_type_g = (CheckBox) findViewById(R.id.building_type_g);
		building_type_h = (CheckBox) findViewById(R.id.building_type_h);
		building_type_i = (CheckBox) findViewById(R.id.building_type_i);
		building_type_j = (CheckBox) findViewById(R.id.building_type_j);
		building_type_k = (CheckBox) findViewById(R.id.building_type_k);
		building_type_l = (CheckBox) findViewById(R.id.building_type_l);

		areaMinEditText = (EditText) findViewById(R.id.area_min);
		areaMaxEditText = (EditText) findViewById(R.id.area_max);

		mActionBar = getSupportActionBar();
		mActionBar.setTitle("房價報報---搜索設定");

		stringPurpose = Setting.getSetting(Setting.keyPurpose, this);
		minHousePriceString = Setting.getSetting(Setting.keyHousePriceMin, this);
		maxHousePriceString = Setting.getSetting(Setting.keyHousePriceMax, this);
		
		stringGroundType = Setting.getSetting(Setting.keyGroundType, this);
		stringBuildingType = Setting.getSetting(Setting.keyBuildingType, this);
		areaMinString = Setting.getSetting(Setting.keyAreaMin, this);
		areaMaxString = Setting.getSetting(Setting.keyAreaMax, this);

		if (stringPurpose.equals("0"))
		{
			radioBuy.setChecked(true);
		} else
		{
			radioSell.setChecked(true);
		}

		if (minHousePriceString.equals("0"))
		{
			// do nothing
		} else
		{
			lowHousePriceEditText.setText(minHousePriceString);
		}
		
		if (maxHousePriceString.equals("0"))
		{
			// do nothing
		}else {
			highHousePriceEditText.setText(maxHousePriceString);
		}
		
		if (stringGroundType.equals("0"))
		{
			ground_type_0.setChecked(true);
			ground_type_1.setChecked(true);
			ground_type_2.setChecked(true);
			ground_type_3.setChecked(true);
			ground_type_4.setChecked(true);
			ground_type_5.setChecked(true);

		} else
		{

			ground_type_0.setChecked(false);
			setChecked(stringGroundType, ground_type_1, "1");
			setChecked(stringGroundType, ground_type_2, "2");
			setChecked(stringGroundType, ground_type_3, "3");
			setChecked(stringGroundType, ground_type_4, "4");
			setChecked(stringGroundType, ground_type_5, "5");
		}

		ground_type_0.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (ground_type_0.isChecked())
				{
					ground_type_1.setChecked(true);
					ground_type_2.setChecked(true);
					ground_type_3.setChecked(true);
					ground_type_4.setChecked(true);
					ground_type_5.setChecked(true);
				} else
				{
					ground_type_1.setChecked(false);
					ground_type_2.setChecked(false);
					ground_type_3.setChecked(false);
					ground_type_4.setChecked(false);
					ground_type_5.setChecked(false);
				}

			}
		});

		if (stringBuildingType.equals("0"))
		{
			building_type_0.setChecked(true);
			building_type_a.setChecked(true);
			building_type_b.setChecked(true);
			building_type_c.setChecked(true);
			building_type_d.setChecked(true);
			building_type_e.setChecked(true);
			building_type_f.setChecked(true);
			building_type_g.setChecked(true);
			building_type_h.setChecked(true);
			building_type_i.setChecked(true);
			building_type_j.setChecked(true);
			building_type_k.setChecked(true);
			building_type_l.setChecked(true);
		} else
		{
			building_type_0.setChecked(false);
			setChecked(stringBuildingType, building_type_a, "a");
			setChecked(stringBuildingType, building_type_b, "b");
			setChecked(stringBuildingType, building_type_c, "c");
			setChecked(stringBuildingType, building_type_d, "d");
			setChecked(stringBuildingType, building_type_e, "e");
			setChecked(stringBuildingType, building_type_f, "f");
			setChecked(stringBuildingType, building_type_g, "g");
			setChecked(stringBuildingType, building_type_h, "h");
			setChecked(stringBuildingType, building_type_i, "i");
			setChecked(stringBuildingType, building_type_j, "j");
			setChecked(stringBuildingType, building_type_k, "k");
			setChecked(stringBuildingType, building_type_l, "l");
		}

		building_type_0.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (building_type_0.isChecked())
				{
					building_type_a.setChecked(true);
					building_type_b.setChecked(true);
					building_type_c.setChecked(true);
					building_type_d.setChecked(true);
					building_type_e.setChecked(true);
					building_type_f.setChecked(true);
					building_type_g.setChecked(true);
					building_type_h.setChecked(true);
					building_type_i.setChecked(true);
					building_type_j.setChecked(true);
					building_type_k.setChecked(true);
					building_type_l.setChecked(true);
				} else
				{
					building_type_a.setChecked(false);
					building_type_b.setChecked(false);
					building_type_c.setChecked(false);
					building_type_d.setChecked(false);
					building_type_e.setChecked(false);
					building_type_f.setChecked(false);
					building_type_g.setChecked(false);
					building_type_h.setChecked(false);
					building_type_i.setChecked(false);
					building_type_j.setChecked(false);
					building_type_k.setChecked(false);
					building_type_l.setChecked(false);
				}

			}
		});

		if (areaMinString.equals("0"))
		{
			// do nothing
		} else
		{
			areaMinEditText.setText(areaMinString);
		}

		if (areaMaxString.equals("0"))
		{
			// do nothing
		} else
		{
			areaMaxEditText.setText(areaMaxString);
		}

	}

	private void setChecked(String content, CheckBox theCheckBox, String theKey)
	{
		if (content.indexOf(theKey) != -1)
		{
			theCheckBox.setChecked(true);
		} else
		{
			theCheckBox.setChecked(false);
		}
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
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(editHousePrice.getWindowToken(), 0);
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
		// hideSoftKeyboard();
	}

}
