package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gdata.client.finance.PortfolioQuery;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.data.finance.PositionFeed;
import com.google.gdata.util.ServiceException;

public class StockWatchPortfolio
{
	private PortfolioManager manager;
	// Handle the case where the shadow is reset, it is set by default.
	private boolean shadow = true;
	// This collection is Set, to only have unique elements.
	Set<StockWatchPosition> stockWatchPositions = null;
	
	private PortfolioEntry portfolioEntry = new PortfolioEntry();

	public StockWatchPortfolio(PortfolioEntry portfolioEntry)
	{
		this.portfolioEntry = portfolioEntry;
	}
	
	public StockWatchPortfolio()
	{
		this.portfolioEntry = new PortfolioEntry();
	}
	
	public void updateTitle(String newTitle)
	{
		portfolioEntry.setTitle(new PlainTextConstruct(newTitle));
		try 
		{
			manager.getPortfolioService().update(new URL(portfolioEntry.getEditLink().getHref()), portfolioEntry);
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
	}
	
	public void setManager(PortfolioManager manager)
	{
		this.manager = manager;
	}
	//Change this.
	public Set<StockWatchPosition> getPositions()
	{
		try
		{
			getPositions1();
		}
		catch(Exception e)
		{
			System.out.println("Bad xml sent by google.");
		}
		return getPositions1();
	}
	
	public Set<StockWatchPosition> getPositions1()
	{
		if(stockWatchPositions == null)
		{
			stockWatchPositions = new HashSet<StockWatchPosition>();
			String positionURL = this.getPortfolioEntry().getFeedLink().getHref();
			PositionFeed positionFeed = null;
			try 
			{
				System.out.println(positionURL);
				positionFeed = manager.getPortfolioService().getFeed(new URL(positionURL+"?returns=true"), PositionFeed.class);
				List<PositionEntry> positions = positionFeed.getEntries();
				for(Iterator positionIterator = positions.iterator(); positionIterator.hasNext();)
				{
					StockWatchPosition stockWatchPosition = new StockWatchPosition((PositionEntry)positionIterator.next()); 
					stockWatchPosition.setManager(this.manager);
					stockWatchPosition.setParentPortfolio(this);
					stockWatchPositions.add(stockWatchPosition);
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
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
		return stockWatchPositions;
	}
	
	public StockWatchPosition getPosition(String portfolioId, String positionId)
	{
		String positionURL = manager.getPortfolioURL()+"/portfolios/"+portfolioId+"/positions/"+positionId;
		StockWatchPosition stockWatchPosition = null;
		try 
		{
			stockWatchPosition = new StockWatchPosition(manager.getPortfolioService().getEntry(new URL(positionURL), PositionEntry.class));
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
		return stockWatchPosition;
	}	
	
	// Stocks are bought using this method, when new stock is being bought. When you are increasing existing stock StockWatchPosition.buyStock() is called
	public void buyStock(String tickerId, int numberOfShares)
	{
		//Change this to so that that the constructor directly accepts data, that would be more efficient than the current way.
		String time = Calendar.getInstance().getTime().toString();
		String amount = String.valueOf(numberOfShares);
		StockWatchTransaction buyTransaction = new StockWatchTransaction("buy", time, amount);
		String transactionURL = manager.getPortfolioURL()+"/positions/"+tickerId+"/transactions"; 
		try 
		{
			buyTransaction = new StockWatchTransaction(manager.getPortfolioService().insert(new URL(transactionURL), buyTransaction.getTransactionEntry()));
			// Maybe would have to be made more efficient;
			StockWatchPosition newStockPosition = new StockWatchPosition(manager.getPortfolioService().getEntry(new URL(""), PositionEntry.class));
			newStockPosition.setManager(manager);
			newStockPosition.setParentPortfolio(this);
			stockWatchPositions.add(newStockPosition);
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
	}

	public PortfolioEntry getPortfolioEntry()
	{
		return portfolioEntry;
	}
}