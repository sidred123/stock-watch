package com.stockwatch;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gdata.data.extensions.Money;
import com.google.gdata.data.finance.PositionData;
import com.google.gdata.data.finance.PositionEntry;

public class StockList extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	public static final int PORTFOLIO_ID = Menu.FIRST;
	Hashtable idSymbolHash = new Hashtable();
	Hashtable SymbolPositionObject = new Hashtable();
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		int idcount = 1;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ScrollView sv = (ScrollView)findViewById(R.id.baseScroll);
		sv.setBackgroundColor(Color.rgb(0,0, 0));
		
		TableLayout t = (TableLayout)findViewById(R.id.table1);
		t.setColumnStretchable(0, true);
		t.setColumnStretchable(1, true);
        t.setColumnStretchable(2, true);
        t.setColumnStretchable(3, true);
        t.setBackgroundColor(Color.rgb(0,0, 0));
        // Let us set the headings.
        TableRow tr = new TableRow(this);
        for(int loop=0;loop<5;loop++)
        {
	        String name = new String();
        	switch(loop)
	        {
	        case 0: name="STOCK\nname";break;
	        case 1: name=" ";break;
	        case 2: name="Shares\nowned";break;
	        case 3: name="Stock\nPrice";break;
	        case 4: name="Day's\ngain";break;
	       
	        }
        	
        	
		  	TextView tv = new TextView(this);
		  	tv.setText(name);
		  	tv.setTextSize(14);
		  	tr.addView(tv);
		  	
        }
        tr.setBackgroundColor(Color.rgb(102, 0, 0));
        t.addView(tr);
	  	
        PortfolioManager portfolioManager = PortfolioManager.getPortfolioManager("tttemper888@gmail.com", "tempster");
        List<StockWatchPortfolio> portfolioList =portfolioManager.getPortfolios();
        if(portfolioList == null)
        {
        	System.out.print("\n\n\nLIST EMPTY\n\n\n");
        }
        Iterator<StockWatchPortfolio> portfolioListIterator = portfolioList.iterator();
        while(portfolioListIterator.hasNext())
        {
        	StockWatchPortfolio portfolio = portfolioListIterator.next();
//        	TableRow tableRow = new TableRow(this);
//        	TextView textView = new TextView(this);
//        	textView.setText(portfolio.getPortfolioEntry().getTitle().getPlainText());
        	Set<StockWatchPosition> positionSet = portfolio.getPositions();
//        	tableRow.addView(textView);
//        	t.addView(tableRow);
        	
        	Iterator<StockWatchPosition> positionSetIterator = positionSet.iterator();
        	while(positionSetIterator.hasNext())
        	{
        		StockWatchPosition position = positionSetIterator.next();
        		
        		TableRow tr1 = new TableRow(this);
       	  		TextView tv1 = new TextView(this);
       	  		String name = new String(position.getPositionEntry().getSymbol().getFullName());
       	  		String sname = new String(position.getPositionEntry().getSymbol().getSymbol());
       	  		if(name.length()>15)
       	  		{
       	  			name = name.substring(0,15) + "..";
       	  		}
       	  		tv1.setText(sname);
       	  		// Let us assign an ID to this text box and store relation in the hash table
       	  		tv1.setId(idcount);
       	  		idSymbolHash.put(new Integer(idcount), sname);
       	  		idcount++;
       	  		tv1.setOnClickListener(this);
       	  		
       	  		
       	  		SymbolPositionObject.put(sname, position);
       	  		
       	  		TextView tv2 = new TextView(this);
       	  		TextView tv3 = new TextView(this);
       	  		TextView tv4 = new TextView(this);
       	  		TextView tv5 = new TextView(this);
       	  		tr1.addView(tv1);
       	  		tr1.addView(tv2);
       	  		tr1.addView(tv3);
       	  		tr1.addView(tv4);
       	  		tr1.addView(tv5);
       	  		t.addView(tr1);
       	  		tv1.setTextSize(14);
       	  		tv2.setTextSize(11);
       	  		tv3.setTextSize(14);
       	  		tv4.setTextSize(14);
       	  		tv5.setTextSize(14);
       	  		
       	  		// Set other IDs as well
       	  	tv2.setId(idcount);
   	  		idSymbolHash.put(new Integer(idcount), sname);
   	  		idcount++;
   	  		tv3.setId(idcount);
	  		idSymbolHash.put(new Integer(idcount), sname);
	  		idcount++;
	  		tv4.setId(idcount);
   	  		idSymbolHash.put(new Integer(idcount), sname);
   	  		idcount++;
   	  		tv5.setId(idcount);
	  		idSymbolHash.put(new Integer(idcount), sname);
	  		idcount++;
	  		
	  		tv2.setOnClickListener(this);
	  		tv3.setOnClickListener(this);
	  		tv4.setOnClickListener(this);
	  		tv5.setOnClickListener(this);
       	  		
       	  		
       	  		tv2.setText(name);
       	  		
       	  	int count = (int)position.getStockCount();
   	  		tv3.setText(Integer.toString(count));
   	  		PositionEntry positionEntry = position.getPositionEntry(); 
   	  		PositionData pdata = positionEntry.getPositionData();		 
   	  		
       	  	
       	  		
       	  	if (pdata.getMarketValue() == null)
	       	 {
	       	       System.out.println("\t\tMarket Value not specified");
	       	 }
	       	 else
	       	 {
	       	 		for (int i = 0; i < pdata.getMarketValue().getMoney().size(); i++) {
	       	 		Money m = pdata.getMarketValue().getMoney().get(i);
	       	         System.out.printf("\t\tThis position is worth %.2f %s.\n", m.getAmount(), m.getCurrencyCode());
	       	         String lastprice = Double.toString(m.getAmount()/position.getStockCount());
	       	         String value = lastprice; // + " " + m.getCurrencyCode();
	       	         tv4.setText(value);
	       	 		}
	       	 }
       	  		
       	  		
       	  	
       	  		if (pdata.getDaysGain() == null) 
       	  		{
       	  			System.out.println("\t\tDay's Gain not specified");
       	  		} 
       	  		else 
       	  		{	
       	  			int limit=5;
       	  			System.out.println(pdata.getDaysGain().getMoney().size());
       	  			for (int i = 0; i < pdata.getDaysGain().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getDaysGain().getMoney().get(i);
       	  				
       	  				
       	  				String gain = Double.toString(money.getAmount()/position.getStockCount());
	       	  			if(gain.length()<5)
	   	  				{
	   	  					limit = gain.length();
	   	  				}
       	  				
       	  				String value = gain.substring(0, limit);// + " " + money.getCurrencyCode();
       	  				
       	  				tv5.setText(value);
       	  				System.out.printf("\t\tThis position made %.2f %s today.\n", money.getAmount(), money.getCurrencyCode());
       	  			}
       	  		}
       	  		if (pdata.getGain() == null) 
       	  		{
       	  			System.out.println("\t\tTotal Gain not specified");
       	  		} 
       	  		else 
       	  		{
       	  			for (int i = 0; i < pdata.getGain().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getGain().getMoney().get(i);
       	  				System.out.printf("\t\tThis position has a total gain of %.2f %s.\n", money.getAmount(), money.getCurrencyCode());
       	  			}
       	  		}
       	  		if (pdata.getMarketValue() == null) 
       	  		{
       	  			System.out.println("\t\tMarket Value not specified");
       	  		} 
       	  		else 
       	  		{
       	  			for (int i = 0; i < pdata.getMarketValue().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getMarketValue().getMoney().get(i);
       	  				System.out.printf("\t\tThis position is worth %.2f %s.\n", money.getAmount(), money.getCurrencyCode());
       	  			}
       	  		}
        	}
        }
        String string = new String("Please Click on stocks to get \nadditional Information...");
        Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, string, duration);
		toast.show();
        
    }	
	
	public void onClick(View v)
	{
		Integer id = new Integer(v.getId());
		// Let us recover the symbol from this
		String symbol = (String)idSymbolHash.get(id);
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, symbol, duration);
		//toast.show();
		
		// Obtaining the position object first
		StockWatchPosition position = (StockWatchPosition)SymbolPositionObject.get(symbol);
	
		
		// Getting the vars
		String fname = new String(position.getPositionEntry().getSymbol().getFullName());
		int count = (int)position.getStockCount();	
		PositionEntry positionEntry = position.getPositionEntry();
  		PositionData pdata = positionEntry.getPositionData();
  		String exchange = positionEntry.getSymbol().getExchange();
  		String percentgain = Double.toString(pdata.getGainPercentage() * 100.0);
  		String originalprice="",lastprice="",daysgain="",totalgain="";
  		if(!(pdata.getCostBasis() == null))
	  		{
   	  		for (int i = 0; i < pdata.getCostBasis().getMoney().size(); i++)
   	  		{
   	         Money m = pdata.getCostBasis().getMoney().get(i);
   	         System.out.printf("\t\tThis position cost %.2f %s.\n", m.getAmount(), m.getCurrencyCode());
   	         originalprice = Double.toString(m.getAmount()/position.getStockCount());
   	  		}
	  			
	  		}
	  		
	  		if(!(pdata.getMarketValue() == null))
	  		{
	  			for (int i = 0; i < pdata.getMarketValue().getMoney().size(); i++) 
	  			{
   	  			Money m = pdata.getMarketValue().getMoney().get(i);
       	        System.out.printf("\t\tThis position is worth %.2f %s.\n", m.getAmount(), m.getCurrencyCode());
       	        lastprice = Double.toString(m.getAmount()/position.getStockCount());
       	    }
	  		}// marketvalue ends
	  	
   	  	if(!(pdata.getDaysGain() == null))
   	  	{
   	  		for (int i = 0; i < pdata.getDaysGain().getMoney().size(); i++) 
   	  		{
	  				Money money = pdata.getDaysGain().getMoney().get(i);
	  				daysgain = Double.toString(money.getAmount()/position.getStockCount());
   	  		}
   	  	}// days gain ends
   	  	
   	  	if(!(pdata.getGain() == null))
   	  	{
       	  	for (int i = 0; i < pdata.getGain().getMoney().size(); i++) 
	  			{
	  				Money money = pdata.getGain().getMoney().get(i);
	  				System.out.printf("\t\tThis position has a total gain of %.2f %s.\n", money.getAmount(), money.getCurrencyCode());
	  				totalgain = Double.toString(money.getAmount()/position.getStockCount());
	  			}
   	  		
   	  	}
	
		
		
		// Let us go to the details page
		Intent intent = new Intent(this, Details.class);
		//intent.setClassName("com.stockwatch","com.stockwatch.Details");
		intent.putExtra("stockcode", symbol);
		intent.putExtra("fullname", fname);
		intent.putExtra("stockcount", Integer.toString(count));
		intent.putExtra("exchange",exchange );
		intent.putExtra("percentgain",percentgain );
		intent.putExtra("lastprice",lastprice );
		intent.putExtra("originalprice",originalprice );
		intent.putExtra("daysgain",daysgain );
		intent.putExtra("totalgain",totalgain);
		startActivity(intent);
		
		
	}

	public void onResume()
	{
		super.onResume();
		String string = new String("Please Click on stocks to get \nadditional Information...");
        Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, string, duration);
		toast.show();
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
    	boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, PORTFOLIO_ID, 0, R.string.menu_portfolio);
        return result;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
	{
        switch (item.getItemId()) {
        case PORTFOLIO_ID:
    		Intent portfolioListIntent = new Intent(this, PortfolioList.class);
    		this.startActivity(portfolioListIntent);
            return true;
        }
       
        return super.onOptionsItemSelected(item);
    }
}