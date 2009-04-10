package com.stockwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StockWatch extends Activity implements OnClickListener
{
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
    	Button loginButton = (Button)findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
    }

	public void onClick(View arg0) 
	{
		Intent stockListIntent = new Intent(this, StockList.class);
		this.startActivity(stockListIntent);
	}
}