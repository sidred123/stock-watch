package com.stockwatch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;

import android.app.Activity;
import android.os.Bundle;

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
      //PortfolioManager pm = new PortfolioManager("tttemper888@gmail.com", "tempster");
      try
      {
	      List<NewsItem> newsItems = new ArrayList<NewsItem>();
	      try
	      {
	    	  /*newsItems = */getNewsItems("NASDAQ", "GOOG");
	      }
	      catch(Exception e)
	      {
	    	  e.printStackTrace();  
	      }
		  Iterator<NewsItem> newsItemIterator = newsItems.iterator();
		  while(newsItemIterator.hasNext())
		  {
		  	NewsItem newsItem = newsItemIterator.next();
		  	System.out.println(newsItem.getTitle());
		  	System.out.println(newsItem.getLink());
		  	System.out.println(newsItem.getDescription());
		  }
      }
      catch(Exception e)
      {
    	e.printStackTrace();  
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

public void /*List<NewsItem>*/ getNewsItems(String stockExchange, String ticker) throws Exception
{
	RSSDigester digester = new RSSDigester();
	String feed = "http://www.google.com/finance?morenews=10&rating=1&q=" + stockExchange + ":" + ticker + "&output=rss";
	URL url =  new URL(feed);
	/*	HttpURLConnection httpSource = (HttpURLConnection)url.openConnection();
	Channel channel = (Channel)digester.parse(httpSource.getInputStream());
	if(channel == null)
	{
		throw new Exception("Cant communicate with " + url);
	}
	Item items[] = channel.findItems();
	List<NewsItem> newsItems = new ArrayList<NewsItem>();
	for(int i = 0; i < items.length; i++)
	{
		newsItems.add(new NewsItem(items[i]));
	}
	return newsItems;*/
}

}