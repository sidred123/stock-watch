package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PortfolioFeed;
import com.google.gdata.util.ServiceException;

public class PortfolioManager 
{
	private static PortfolioManager portfolioManager;
	private static FinanceService portfolioService;
	private static GoogleAccountConnector gac;
	private String portfolioURL ="http://finance.google.com/finance/feeds/default/portfolios";
	private static List<StockWatchPortfolio> stockWatchPortfolios = 	new ArrayList<StockWatchPortfolio>(); 						  
	public static PortfolioManager getPortfolioManager(String username, String password)
	{
		if(portfolioManager == null)
		{
			portfolioService = new FinanceService("StockWatchService");
			gac = new GoogleAccountConnector();
			String authenticationToken = gac.getAuthToken(username, password);
			portfolioService.setUserToken(authenticationToken);
			portfolioManager = new PortfolioManager();
		}
		return portfolioManager;
	}

	public FinanceService getPortfolioService()
	{
		return portfolioService;
	}
	
	public String getPortfolioURL()
	{
		return portfolioURL;
	}
	
	//This method by default also returns the performance details of the portfolios.
	public List<StockWatchPortfolio> getPortfolios()
	{
		if(stockWatchPortfolios.isEmpty())
		{
			try
			{
				PortfolioFeed portfolioFeed = portfolioService.getFeed(new URL(portfolioURL+"?returns=true"), PortfolioFeed.class);
				List<PortfolioEntry> portfolios = portfolioFeed.getEntries();
				for(Iterator<PortfolioEntry> portfolioIterator = portfolios.iterator(); portfolioIterator.hasNext();)
				{
					StockWatchPortfolio stockWatchPortfolio = new StockWatchPortfolio((PortfolioEntry)portfolioIterator.next()); 
					stockWatchPortfolio.setManager(this);
					stockWatchPortfolios.add(stockWatchPortfolio);
				}
				return stockWatchPortfolios;
			}
			catch(MalformedURLException e)
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
			return null;
		}
		return stockWatchPortfolios;
	}
	
	// Time this method, depending on how much time it takes we would have to change the implementation to get portfolio locally. 
	public StockWatchPortfolio getPortfolio(String portfolioID)
	{
		try 
		{
			return new StockWatchPortfolio(portfolioService.getEntry(new URL(portfolioURL+"/"+portfolioID), PortfolioEntry.class));
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
		return null;
	}
	
	public void addPortfolio(StockWatchPortfolio portfolio)
	{
		try 
		{
			portfolioService.insert(new URL(portfolioURL), portfolio.getPortfolioEntry());
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
	// Handle the case where the portfolio is not empty.
	public void deletePortfolio(StockWatchPortfolio portfolio)
	{
		try 
		{
			portfolioService.delete(new URL(portfolio.getPortfolioEntry().getEditLink().getHref()));
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
}