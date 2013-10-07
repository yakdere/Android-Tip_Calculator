package com.yakdere.tipcalc;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalc extends Activity {
	//Constructors
	private final double TIP_TEN = 0.10;
	private final double TIP_FIF = 0.15;
	private final double TIP_TWE = 0.20;

	//View Components
	EditText input;
	TextView tip_amount, sum;
	Button b10;
	Button b15;
	Button b20;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipcalc);

		input = (EditText) findViewById(R.id.etAmount);
		tip_amount = (TextView) findViewById(R.id.tvTipAmount);
		sum = (TextView) findViewById(R.id.tvSum);
		b10 = (Button) findViewById(R.id.b10);
		b15 = (Button) findViewById(R.id.b15);
		b20 = (Button) findViewById(R.id.b20);

		b10.setOnClickListener(this.listener);
		b15.setOnClickListener(this.listener);
		b20.setOnClickListener(this.listener);

		showSoftKeyboard(input);
	}

	public void showSoftKeyboard(View view){
		if(view.requestFocus()){
			InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);	
		}
	}

	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.b10: 
				setTipandTotal(TIP_TEN);
				break;
			case R.id.b15:
				setTipandTotal(TIP_FIF);
				break;
			case R.id.b20:
				setTipandTotal(TIP_TWE);
				break;
			}
		}
	};

	private void setTipandTotal(double t) {
		Double tip = t*Double.valueOf(input.getText().toString());
		tip_amount.setText(String.valueOf(new DecimalFormat("##.##").format(tip)));
		sum.setText('$'+String.valueOf(new DecimalFormat("##.##").format(tip+Double.valueOf(input.getText().toString()))));
		hideSoftKeyboard(input);
	}

	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calc, menu);
		return true;
	}

}
