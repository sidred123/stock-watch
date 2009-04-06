package com.stockwatch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.google.gdata.data.finance.PositionData;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.data.finance.TransactionEntry;
import com.google.gdata.data.finance.TransactionFeed;
import com.google.gdata.util.ServiceException;

// What happens when all stock is sold? Handle that.
// Or if you are buying for the first time?

public class StockWatchPosition
{
	private PortfolioManager manager;
	private StockWatchPortfolio parentPortfolio;
	private PositionEntry positionEntry;
		
	public StockWatchPosition(PositionEntry positionEntry)
	{
		this.positionEntry = positionEntry;
	}
	
	public StockWatchPosition()
	{
		this.positionEntry = new PositionEntry();
	}
	
	public void setManager(PortfolioManager manager)
	{
		this.manager = manager;
	}
	
	public void setParentPortfolio(StockWatchPortfolio portfolio)
	{
		parentPortfolio = portfolio;
	}
	
	public List<StockWatchTransaction> getTransactions()
	{
		String transactionURL = this.getPositionEntry().getFeedLink().getHref(); 
		List<StockWatchTransaction> stockWatchTransactions = new ArrayList<StockWatchTransaction>();
		try 
		{
			TransactionFeed transactionFeed = manager.getPortfolioService().getFeed(new URL(transactionURL), TransactionFeed.class);
			List<TransactionEntry> transactions = transactionFeed.getEntries();
			for(Iterator transactionIterator = transactions.iterator(); transactionIterator.hasNext();)
			{
				StockWatchTransaction stockWatchTransaction = new StockWatchTransaction((TransactionEntry)transactionIterator.next()); 
				stockWatchTransaction.setManager(manager);
				stockWatchTransactions.add(stockWatchTransaction);
			}
			return stockWatchTransactions;
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
		return stockWatchTransactions;
	}
	
	public StockWatchTransaction getTransaction()
	{
		StockWatchTransaction stockWatchTransaction = null;
		return stockWatchTransaction;
	}
	
	public void deleteTransaction()
	{
		try 
		{
			URL deleteURL = new URL(positionEntry.getEditLink().getHref());
			manager.getPortfolioService().delete(deleteURL);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (ServiceException e) 
		{
			e.printStackTrace();
		}
	}
	// This method is called when you are increassing existing stock.
	public void buyStock(int numberOfShares)
	{
		//Change this to so that that the constructor directly accepts data, that would be more efficient than the current way.
		String time = Calendar.getInstance().getTime().toString();
		String amount = String.valueOf(numberOfShares);
		
		String tickerId = positionEntry.getSymbol().getSymbol();
		
		StockWatchTransaction buyTransaction = new StockWatchTransaction("buy", time, amount);
		String transactionURL = manager.getPortfolioURL()+"/positions/"+tickerId+"/transactions"; 
		try 
		{
			buyTransaction = new StockWatchTransaction(manager.getPortfolioService().insert(new URL(transactionURL), buyTransaction.getTransactionEntry()));
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
		// Maybe would have to be made more efficient;
		positionEntry.getPositionData().setShares(positionEntry.getPositionData().getShares() + numberOfShares);
	}
	
	public void sellStock(int numberOfShares)
	{
		double existingStockCount = positionEntry.getPositionData().getShares();
		// If numberOfShares is greater than existingStockCount, then delete the entire stock.
		// Make changes to throw exception later.
		String time = Calendar.getInstance().getTime().toString();
		String amount = String.valueOf(numberOfShares);
		StockWatchTransaction sellTransaction = new StockWatchTransaction("sell", time, amount);
		String tickerId = positionEntry.getSymbol().getSymbol();
		String transactionURL = manager.getPortfolioURL()+"/positions/"+tickerId+"/transactions"; 
		try 
		{
			sellTransaction = new StockWatchTransaction(manager.getPortfolioService().insert(new URL(transactionURL), sellTransaction.getTransactionEntry()));
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
		// Maybe would have to be made more efficient;
		positionEntry.getPositionData().setShares(positionEntry.getPositionData().getShares() + numberOfShares);
	}
	
	public double getStockCount()
	{
		PositionData positionData = positionEntry.getPositionData();
		return positionData.getShares();
	}
	
	public PositionEntry getPositionEntry()
	{
		return positionEntry;
	}
	
	public void getWeeklyPerformancePlot() throws IOException
	{
		String historyURL = "http://www.google.com/finance/historical?q="+this.getPositionEntry().getSymbol()+"&histperiod=daily&output=csv";
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
		Calendar currentTime = Calendar.getInstance();
		Date currentDate = currentTime.getTime();
		int currentDay = currentDate.getDate();
		int currentMonth = currentDate.getMonth();
		int currentYear = currentDate.getYear();
		int previousDay = currentDate.getDate() - 7;
		int previousMonth = 0;
		int previousYear = 0;
		if(previousDay < 1)
		{
			int month = currentMonth - 1;
			if(month < 1)
			{
				previousYear = currentYear - 1;
				previousMonth = 12;
			}
			else
			{
				previousYear = currentYear;
			}
			Calendar calendar = new GregorianCalendar(previousYear, month, 1);
			int noOfDays = calendar.getActualMaximum(month);
			previousDay = noOfDays + previousDay;
		}
		else
		{
			previousMonth = currentMonth;
			previousYear = currentYear;
		}
		historyURL  += "&startdate="+months[previousMonth].substring(0,3)+" "+previousDay+", "+previousYear;
		historyURL  += "&enddate="+months[currentMonth].substring(0,3)+" "+currentDay+", "+currentYear;
		URLConnection connection = null;
		InputStream in = null;
		OutputStream out = new BufferedOutputStream(new FileOutputStream("historicaldata.txt"));
		
		URL url = new URL(historyURL);
		connection = url.openConnection();
		in = connection.getInputStream();
		byte[] buffer = new byte[1024];
		int numRead;
		long numWritten = 0;
		while((numRead = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, numRead);
			numWritten += numRead;
		}
        in.close();
		out.close();
		BufferedReader inReader = new BufferedReader(new FileReader("historicaldata.txt"));
	    String historicalDataString = "";
	    while((historicalDataString += inReader.readLine()+'\n') != null) 
	    {
	    	;
        }
	    inReader.close();
	    String[] dailyData = historicalDataString.split("\n");
	    dailyData[0] = "Dont use this value.";
	    for(int i = 1; i < dailyData.length; i++)
	    {
	    	dailyData[i] = dailyData[i].split(",")[4]; 
	    }
	}
	
	public void getMonthlyPerformancePlot(int noOfCoordinates) throws Exception
	{
		String historyURL = "http://www.google.com/finance/historical?q="+this.getPositionEntry().getSymbol()+"&histperiod=daily&output=csv";
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
		Calendar currentTime = Calendar.getInstance();
		Date currentDate = currentTime.getTime();
		int currentDay = currentDate.getDate();
		int currentMonth = currentDate.getMonth();
		int currentYear = currentDate.getYear();
		int previousDay = 1;
		historyURL  += "&startdate="+months[currentMonth].substring(0,3)+" "+previousDay+", "+currentYear;
		historyURL  += "&enddate="+months[currentMonth].substring(0,3)+" "+currentDay+", "+currentYear;
		URLConnection connection = null;
		InputStream in = null;
		OutputStream out = new BufferedOutputStream(new FileOutputStream("historicaldata.txt"));
		
		URL url = new URL(historyURL);
		connection = url.openConnection();
		in = connection.getInputStream();
		byte[] buffer = new byte[1024];
		int numRead;
		long numWritten = 0;
		while((numRead = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, numRead);
			numWritten += numRead;
		}
        in.close();
		out.close();
		BufferedReader inReader = new BufferedReader(new FileReader("historicaldata.txt"));
	    String historicalDataString = "";
	    while((historicalDataString += inReader.readLine()+'\n') != null) 
	    {
	    	;
        }
	    inReader.close();
	    String[] dailyData = historicalDataString.split("\n");
	    dailyData[0] = "Dont use this value.";
	    Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
		int noOfDays = calendar.getActualMaximum(currentMonth);
		int increment = noOfDays/noOfCoordinates;
	    for(int i = 1; i < dailyData.length; i += increment)
	    {
	    	dailyData[i] = dailyData[i].split(",")[4]; 
	    }
	}
	
	public void getYearlyPerformancePlot(int noOfCoordinates) throws Exception
	{
		String historyURL = "http://www.google.com/finance/historical?q="+this.getPositionEntry().getSymbol()+"&histperiod=daily&output=csv";
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
		Calendar currentTime = Calendar.getInstance();
		Date currentDate = currentTime.getTime();
		int currentDay = currentDate.getDate();
		int currentMonth = currentDate.getMonth();
		int currentYear = currentDate.getYear();
		int previousDay = 1;
		historyURL  += "&startdate="+months[1].substring(0,3)+" "+previousDay+", "+currentYear;
		historyURL  += "&enddate="+months[currentMonth].substring(0,3)+" "+currentDay+", "+currentYear;
		URLConnection connection = null;
		InputStream in = null;
		OutputStream out = new BufferedOutputStream(new FileOutputStream("historicaldata.txt"));
		
		URL url = new URL(historyURL);
		connection = url.openConnection();
		in = connection.getInputStream();
		byte[] buffer = new byte[1024];
		int numRead;
		long numWritten = 0;
		while((numRead = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, numRead);
			numWritten += numRead;
		}
        in.close();
		out.close();
		BufferedReader inReader = new BufferedReader(new FileReader("historicaldata.txt"));
	    String historicalDataString = "";
	    while((historicalDataString += inReader.readLine()+'\n') != null) 
	    {
	    	;
        }
	    inReader.close();
	    String[] dailyData = historicalDataString.split("\n");
	    dailyData[0] = "Dont use this value.";
	    Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
		int noOfDays = calendar.getActualMaximum(currentMonth);
		int increment = noOfDays/noOfCoordinates;
	    for(int i = 1; i < dailyData.length; i += increment)
	    {
	    	dailyData[i] = dailyData[i].split(",")[4]; 
	    }
	}
}