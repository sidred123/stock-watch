package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.finance.Commission;
import com.google.gdata.data.finance.PortfolioEntry;
import com.google.gdata.data.finance.PositionEntry;
import com.google.gdata.data.finance.Price;
import com.google.gdata.data.finance.TransactionData;
import com.google.gdata.data.finance.TransactionEntry;
import com.google.gdata.util.ServiceException;

public class StockWatchTransaction 
{
	TransactionData data;
	PortfolioManager manager;
	
	private TransactionEntry transactionEntry;
	
	public StockWatchTransaction(TransactionEntry transactionEntry)
	{
		this.transactionEntry = transactionEntry;
	}
	
	public StockWatchTransaction()
	{
		this.transactionEntry = new TransactionEntry();
	}
	
	public StockWatchTransaction(String type, String date, String shares)//, String price, String commission, String currency, String notes)
	{
		data.setType(type);
		data.setDate(DateTime.parseDateTime(date + "T00:00:00.000Z"));
		data.setShares(Double.valueOf(shares).doubleValue());
		Price p = new Price();
	//	p.addMoney(new Money(Double.valueOf(price).doubleValue(), currency));
	//	data.setPrice(p);
	//	Commission c = new Commission();
	//	c.addMoney(new Money(Double.valueOf(commission).doubleValue(), currency));
	//	data.setCommission(c);
	//	data.setNotes(notes);
		transactionEntry.setTransactionData(data);
	}
	
	public void updateTitle(String newTitle)
	{
		transactionEntry.setTitle(new PlainTextConstruct(newTitle));
		String transactionEditURL = transactionEntry.getEditLink().getHref();
		try 
		{
			manager.getPortfolioService().update(new URL(transactionEditURL), transactionEntry);
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
	
	public TransactionEntry getTransactionEntry()
	{
		return transactionEntry;
	}
}