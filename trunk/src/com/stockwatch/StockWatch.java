package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.data.extensions.Money;
import com.google.gdata.data.finance.DaysGain;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PortfolioFeed;
import com.google.gdata.data.finance.PositionData;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.util.ServiceException;

public class StockWatch extends Activity {
    /** Called when the activity is first created. */
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TableLayout t = (TableLayout)findViewById(R.id.table1);
        
        PortfolioManager pm = new PortfolioManager("tttemper888@gmail.com", "tempster");
        List<StockWatchPortfolio> portfolioList =pm.getPortfolios();
        if(portfolioList == null)
        {
        	System.out.print("\n\n\nLIST EMPTY\n\n\n");
        }
        Iterator<StockWatchPortfolio> portfolioIterator = portfolioList.iterator();
        while(portfolioIterator.hasNext())
        {
        	StockWatchPortfolio portfolio = portfolioIterator.next();
        	TableRow tableRow = new TableRow(this);
        	TextView textView = new TextView(this);
        	textView.setText(portfolio.getPortfolioEntry().getTitle().getPlainText());
        	Set<StockWatchPosition> positionSet = portfolio.getPositions();
        	tableRow.addView(textView);
        	t.addView(tableRow);
        	
        	Iterator<StockWatchPosition> positionIterator = positionSet.iterator();
        	while(positionIterator.hasNext())
        	{
        		StockWatchPosition position = positionIterator.next();
        		TableRow tr1 = new TableRow(this);
       	  		TextView tv1 = new TextView(this);
       	  		tv1.setText(position.getPositionEntry().getSymbol().getFullName());
       	  		TextView tv2 = new TextView(this);
       	  		tv2.setText(Double.toString(position.getStockCount()));
       	  		PositionEntry positionEntry = position.getPositionEntry(); 
       	  		PositionData pdata = positionEntry.getPositionData();		 
       	  		TextView tv3 = new TextView(this);	
       	  		tr1.addView(tv1);
       	  		tr1.addView(tv2);
       	  		tr1.addView(tv3);
       	  		t.addView(tr1);
       	  	
       	  		if (pdata.getDaysGain() == null) 
       	  		{
       	  			System.out.println("\t\tDay's Gain not specified");
       	  		} 
       	  		else 
       	  		{
       	  			System.out.println(pdata.getDaysGain().getMoney().size());
       	  			for (int i = 0; i < pdata.getDaysGain().getMoney().size(); i++) 
       	  			{
       	  				Money money = pdata.getDaysGain().getMoney().get(i);
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
    }	
	
	public String[] getPortfolios()
	{
		System.out.println("Authenticating...");
        FinanceService myService = new FinanceService("MyService");
        GoogleAccountConnector gac = new GoogleAccountConnector();
		try
		{
			String authToken = gac.getAuthToken("tttemper888@gmail.com", "tempster");
			System.out.println(authToken);
			myService.setUserToken(authToken);
			System.out.println("Successfully authenticated");
		}
		catch(Exception e)
		{
			System.out.println("Authentication Failure");
			e.printStackTrace();
			String[] str = new String[1];
			str[1] = e.getMessage();
			return str;
		}
		
		System.out.println("Getting Portfolios.");
		String[] portfolios = new String[2];
		try 
		{
			PortfolioFeed portfolioFeed = myService.getFeed(new URL("http://finance.google.com/finance/feeds/default/portfolios"), PortfolioFeed.class);
			System.out.println("I am here.");
			for(int i = 0; i < portfolioFeed.getEntries().size(); i++)
			{
				PortfolioEntry portfolioEntry = portfolioFeed.getEntries().get(i);
				portfolios[i] = portfolioEntry.getTitle().getPlainText();
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ServiceException e) 
		{
			e.printStackTrace();
		}
		return portfolios;
	}
}