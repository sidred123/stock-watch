package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PortfolioFeed;
import com.google.gdata.util.ServiceException;
import com.stockwatch.news.NewsItem;
import com.stockwatch.news.NewsManager;

public class StockWatch extends Activity {
    /** Called when the activity is first created. */
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String $rssUrl = NewsManager.generateURL("NASDAQ", "GOOG");
		NewsManager newsManager = new NewsManager($rssUrl);
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
		TableLayout table = (TableLayout)findViewById(R.id.table1);
		while(newsItemIterator.hasNext())
		{
			NewsItem newsItem = newsItemIterator.next();
			TableRow tableRow1 = new TableRow(this);
			TextView textView1 = new TextView(this);
			TableRow tableRow2 = new TableRow(this);
			TextView textView2 = new TextView(this);
			TableRow tableRow3 = new TableRow(this);
			TextView textView3 = new TextView(this);
			textView1.setText(newsItem.getTitle());
			textView2.setText(newsItem.getLink());
			textView3.setText(newsItem.getDescription());
	  	  	tableRow1.addView(textView1);
	  	  	tableRow2.addView(textView2);
	  	  	tableRow3.addView(textView3);
	  	  	table.addView(tableRow1);
	  	  	table.addView(tableRow2);
	  	  	table.addView(tableRow3);
		}
		/*      TableLayout t = (TableLayout)findViewById(R.id.table1);
        
        PortfolioManager pm = new PortfolioManager("tttemper888@gmail.com", "tempster");
        List<StockWatchPortfolio> l1 =pm.getPortfolios();
        if(l1 == null)
        {
        	System.out.print("\n\n\nLIST EMPTY\n\n\n");
        }
      Iterator<StockWatchPortfolio> ip2 = l1.iterator();
      ip2.next().getPositions();
      while(ip2.hasNext())
      {
    	  StockWatchPortfolio swpor = ip2.next();
    	  
//    	  TableRow tr = new TableRow(this);
//    	  TextView tv = new TextView(this);
//    	  tv.setText(swpor.getPortfolioEntry().getTitle().getPlainText());
//    	  tr.addView(tv);
//    	  t.addView(tr);
    	  System.out.println(swpor.getPortfolioEntry().getTitle().getPlainText());
    	  Set<StockWatchPosition> s1 = swpor.getPositions();
    	  System.out.println("Size: "+s1.size());
    	  
    	  Iterator<StockWatchPosition> i1 = s1.iterator();
    	  while(i1.hasNext())
    	  {
    		 StockWatchPosition swpos = i1.next();
    		 TableRow tr1 = new TableRow(this);
       	  	TextView tv1 = new TextView(this);
       	  	tv1.setText(swpos.getPositionEntry().getSymbol().getFullName());
       	  	TextView tv2 = new TextView(this);
       	  	tv2.setText(Double.toString(swpos.getStockCount()));
       	  	PositionEntry temp_pe = swpos.getPositionEntry(); 
       	  	PositionData pdata = temp_pe.getPositionData();		 
       	  	TextView tv3 = new TextView(this);	
       //	  	DaysGain m = pdata.getDaysGain();
//       	  	
       //	  	tv3.setText(m.toString());
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
        
        
        
        
//		setListAdapter(new ArrayAdapter<String>(this, R.layout.notes_row, message));
//		getListView().setTextFilterEnabled(true);*/
    }
	
	public String[] getPortfolios()
	{
		System.out.println("Authenticating...");
        FinanceService myService = new FinanceService("MyService");
        GoogleAccountConnector gac = new GoogleAccountConnector();
		try
		{
			String authToken = gac.getAuthToken("tttemper888@gmail.com", "tempster");
			//myService.getAuthToken("tttemper888@gmail.com", "tempster", "", "", "MyService", "SubObjective2");
			//myService.setUserCredentials("tttemper888@gmail.com", "tempster");
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