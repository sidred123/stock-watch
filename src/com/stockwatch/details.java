package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.data.extensions.Money;
import com.google.gdata.data.finance.DaysGain;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PortfolioFeed;
import com.google.gdata.data.finance.PositionData;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.util.ServiceException;


public class details extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		
		
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ScrollView sv = (ScrollView)findViewById(R.id.baseScroll);
		sv.setBackgroundColor(Color.rgb(0,0, 0));
		String stockcode = "";
        Bundle somedata = getIntent().getExtras();
        if(somedata != null)
        {
        	stockcode = somedata.getString("stockcode") + " is the code you recieved!";
        }
        
        // Let us set up the UI with these variables
        TableLayout table = (TableLayout)findViewById(R.id.table1);
		table.setColumnStretchable(0, true);
		table.setColumnStretchable(1, true);
		table.setBackgroundColor(Color.rgb(0,0, 0));
		
		TableRow tr = new TableRow(this);
		TextView tv1 = new TextView(this);
		tv1.setText("Stock Details");
		tv1.setTextSize(16);
		tr.addView(tv1);
		tr.setBackgroundColor(Color.argb(200,102, 0, 0));
		
		table.addView(tr);
		
		String key="",value="";
		for(int loop=1;loop<=9;loop++)
		{
			switch(loop)
			{
				
				case 1: key = "Name"; value = somedata.getString("fullname");break;
				case 2: key = "Symbol "; value = somedata.getString("stockcode");break;
				case 3: key = "Stock Count"; value = somedata.getString("stockcount");break;
				case 4: key = "Exchange"; value=somedata.getString("exchange");break;
				case 5: key = "Last Price"; value=somedata.getString("lastprice");break;
				case 6: key = "Purchased at"; value=somedata.getString("originalprice");break;
				case 7: key = "Percent Gain"; value=somedata.getString("percentgain")+"%";break;
				case 8: key = "Day's Gain"; value=somedata.getString("daysgain").substring(0, 5);break;
				case 9: key = "Total Gain"; value=somedata.getString("totalgain");break;
			}
			
			 tr = new TableRow(this);
			tv1 = new TextView(this);
			tr.setBackgroundColor(Color.argb((60+(loop*10)),102, 0, 0));
			TextView tv2 = new TextView(this);
			tv1.setText(key);
			tv2.setText(value);
			tr.addView(tv1);
			tr.addView(tv2);
			table.addView(tr);
			
			tv1.setTextSize(13);
			tv2.setTextSize(14);
			
			
		}
//		tr = new TableRow(this);
//		TextView tvx = new TextView(this);
//		tvx.setText(" ");
//		tr.addView(tvx);
//		Button b1 = new Button(this);
//		b1.setText("Edit");
//		tr.addView(b1);
//		TextView tvx2 = new TextView(this);
//		tvx2.setText(" ");
//		tr.addView(tvx2);
//		Button b2 = new Button(this);
//		b2.setText("Back");
//		tr.addView(b2);
//		
//		
//		table.addView(tr);
		

	}// oncreate function ends

}// class declaration ends
