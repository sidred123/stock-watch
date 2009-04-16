package com.stockwatch;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class StockWatch extends Activity implements OnClickListener
{
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
    	Button loginButton = (Button)findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		
		AbsoluteLayout ab = (AbsoluteLayout)findViewById(R.id.absolute1);
		
		ImageView iv = new ImageView(this);
        Resources res = getResources();
        Drawable d = res.getDrawable(R.drawable.bg2);
        iv.setImageDrawable(d);
        ab.setBackgroundDrawable(d);
		
        TextView tvU = (TextView)findViewById(R.id.android_username);
        tvU.setTextColor(Color.rgb(255,255,255));
        tvU.setTextSize(16);
        tvU.setBackgroundColor(Color.rgb(0,0,255));
        //tvU.setBackgroundColor(Color.rgb(102,0,0));
        
        TextView tvP = (TextView)findViewById(R.id.android_pwd);
        tvP.setBackgroundColor(Color.rgb(0,0,255));
        //tvP.setBackgroundColor(Color.rgb(102,0,0));
        tvP.setTextSize(16);
        tvP.setTextColor(Color.rgb(255,255,255));
    
        CheckBox ch = (CheckBox)findViewById(R.id.remember);
        ch.setBackgroundColor(Color.rgb(0,0,255));
    }

	public void onClick(View arg0) 
	{
		Intent stockListIntent = new Intent(this, StockList.class);
		this.startActivity(stockListIntent);
	}
}