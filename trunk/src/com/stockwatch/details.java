package com.stockwatch;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.stockwatch.news.NewsItem;
import com.stockwatch.news.NewsManager;


public class Details extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
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
		String ticker = "";
		for(int loop=1;loop<=9;loop++)
		{
			switch(loop)
			{
				
				case 1: key = "Name"; value = somedata.getString("fullname");break;
				case 2: key = "Symbol "; value = somedata.getString("stockcode");ticker = value;break;
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
		
		
		ScrollView newsScroll = (ScrollView)findViewById(R.id.baseScroll);
		newsScroll.setBackgroundColor(Color.rgb(0,0, 0));
		
		TableLayout newsItemTable = (TableLayout)findViewById(R.id.table1);
		newsItemTable.setColumnStretchable(0, true);
		newsItemTable.setColumnStretchable(1, true);
		newsItemTable.setBackgroundColor(Color.rgb(0,0, 0));
		
		TableRow newsTableTitleRow = new TableRow(this);
		TextView newsTableTitleText = new TextView(this);
		newsTableTitleText.setText("Stock Details");
		newsTableTitleText.setTextSize(16);
		newsTableTitleRow.addView(newsTableTitleText);
		newsTableTitleRow.setBackgroundColor(Color.argb(200,102, 0, 0));
		
		newsItemTable.addView(newsTableTitleRow);
		
		String newsUrl = NewsManager.generateURL(ticker);
		NewsManager newsManager = new NewsManager(newsUrl);
		try
		{
			newsManager.parse();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		List<NewsItem> newsItems = newsManager.getChannel().getItems();
		Iterator<NewsItem> newsItemIterator = newsItems.iterator();
		while(newsItemIterator.hasNext())
		{
			NewsItem newsItem = newsItemIterator.next();
			TableRow newsTableItemRow = new TableRow(this);
			TextView newsTableItemText = new TextView(this);
			newsTableItemText.setText(newsItem.getTitle());
			newsTableItemText.setTextSize(16);
			newsTableItemRow.addView(newsTableItemText);
			newsItemTable.addView(newsTableItemRow);
		}
		
		//WebView webView = (WebView)findViewById(R.id.webview);
		//webView.getSettings().setJavaScriptEnabled(true);
		//webView.loadUrl("http://www.google.com");
	}// oncreate function ends
}// class declaration ends
