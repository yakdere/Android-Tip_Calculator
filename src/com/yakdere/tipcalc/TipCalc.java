package com.yakdere.tipcalc;

import java.text.DecimalFormat;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TipCalc extends Activity {
	boolean wheelScrolled;
	int tip_rate;

	//View Components
	EditText input;
	TextView tip_amount, sum;
	WheelView wvtip_rate;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipcalc);
		
		wheelScrolled = false;
		tip_rate = 0;
		
		input = (EditText) findViewById(R.id.etAmount);
		input.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {	
			}
			@Override
			public void beforeTextChanged(CharSequence s, int arg1,
					int arg2, int arg3) {	
			}
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				double amount;
				try {
					amount = Double.parseDouble(s.toString());
					Log.i("Text Watcher", "Text changed");
					if (wheelScrolled) {
						setTipandTotal(tip_rate, amount);
					}	
				} catch (NumberFormatException e) {
					amount = 0.0;
				}
			}
			
			
		});
		/*
		if ( input.getText().toString() != null && input.getText().toString().length() > 0) {
			amount = Double.parseDouble(input.getText().toString());
			Toast.makeText(this, "Your check is: "+amount, Toast.LENGTH_SHORT).show();
		} else {
			amount = 0.0;
			Toast.makeText(this, "Please enter your check value.", Toast.LENGTH_SHORT).show();
		}
		*/
		tip_amount = (TextView) findViewById(R.id.tvTipAmount);
		sum = (TextView) findViewById(R.id.tvSum);
		wvtip_rate = (WheelView) findViewById(R.id.wvTipRate);
		wvtip_rate.setViewAdapter(new NumericWheelAdapter(this, 0, 99));
		wvtip_rate.setCurrentItem((int)(Math.random() * 10));
		wvtip_rate.addChangingListener(changedListener);
		wvtip_rate.addScrollingListener(scrolledListener);
		wvtip_rate.setCyclic(true);
		wvtip_rate.setInterpolator(new AnticipateOvershootInterpolator());
		
		showSoftKeyboard(input);
	}
	
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
		
		@Override
		public void onScrollingStarted(WheelView wheel) {
			wheelScrolled = true;
		}
		
		@Override
		public void onScrollingFinished(WheelView wheel) {
			wheelScrolled = false;
			tip_rate = wvtip_rate.getCurrentItem();
			Log.i("Scroll Listener", "tip rate is" +tip_rate);	
			if (input.getText().toString() != null) {
				setTipandTotal(tip_rate, Double.valueOf(input.getText().toString()));
			}
			//setTipandTotal(tip_rate);
		}
	};
	OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if(!wheelScrolled) {
				tip_rate = wvtip_rate.getCurrentItem();
				Log.i("Changed Listener", "tip rate is" +tip_rate);	
				//setTipandTotal(tip_rate);
			}
			
		}
	};
		public void showSoftKeyboard(View view){
		if(view.requestFocus()){
			InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);	
		}
	}
		public void hideSoftKeyboard(View v) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}

	
	private void setTipandTotal(int t, double v) {
		Log.i("Set Tip and Total Method", "invoked");
		Double rate;
		try { 
			rate =  (double) (t * 0.01);
			Double tip = rate * v;
			tip_amount.setText("$ " + String.valueOf(new DecimalFormat("##.##").format(tip)));
			sum.setText('$'+String.valueOf(new DecimalFormat("##.##").format(tip + v)));
		} catch (NumberFormatException e) {
			rate = 0.0;
			Log.e("Error", e.toString());
		}
		hideSoftKeyboard(input);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calc, menu);
		return true;
	}

}
