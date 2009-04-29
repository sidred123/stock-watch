package com.stockwatch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gdata.client.finance.FinanceService;
import com.google.gdata.data.extensions.Money;
import com.google.gdata.data.finance.Price;
import com.google.gdata.data.finance.TransactionData;
import com.google.gdata.data.finance.TransactionEntry;
import com.google.gdata.util.ServiceException;

public class BuySell extends Activity implements OnClickListener
{
	EditText tickerEdit;
	EditText countEdit;
	EditText priceEdit;
	StockWatchPortfolio portfolio;
	boolean action;
	//Change this.
	PortfolioManager portfolioManager = PortfolioManager.getPortfolioManager("tttemper888@gmail.com", "tempster");
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action);
		
		Bundle intentData = getIntent().getExtras();
		String portfolioId = intentData.getString("portfolio");
		action = intentData.getBoolean("action");
		portfolio = portfolioManager.getPortfolio(portfolioId);
		
		TableLayout tableLayout = (TableLayout)findViewById(R.id.buyselltable);
		
		TableRow tickerRow = new TableRow(this);
		TableRow countRow = new TableRow(this);
		TableRow priceRow = new TableRow(this);
		TableRow submitRow = new TableRow(this);
		
		TextView tickerText = new TextView(this);
		TextView countText = new TextView(this);
		TextView priceText = new TextView(this);
	
		tickerEdit = new EditText(this);
		countEdit = new EditText(this);
		priceEdit = new EditText(this);
		
		tickerText.setText("Stock Ticker:");
		countText.setText("Number of shares:");
		priceText.setText("At price:");
				
		tickerRow.addView(tickerText);
		tickerRow.addView(tickerEdit);
		
		countRow.addView(countText);
		countRow.addView(countEdit);
		
		priceRow.addView(priceText);
		priceRow.addView(priceEdit);
		
		Button submitButton = new Button(this);
		
		submitRow.addView(submitButton);
		if(action)
		{
			submitButton.setText("Buy");
		}
		else
		{
			submitButton.setText("Sell");
		}
		submitButton.setOnClickListener(this);
		
		tableLayout.addView(tickerRow);
		tableLayout.addView(countRow);
		tableLayout.addView(priceRow);
		tableLayout.addView(submitRow);
	}

	public void onClick(View v) 
	{
		String symbol = tickerEdit.getText().toString();
		String count = countEdit.getText().toString();
		String price = priceEdit.getText().toString();
		TransactionEntry transaction = new TransactionEntry();
		TransactionData transactionData = new TransactionData();
		
		if(action)
			transactionData.setType("Buy");
		else
			transactionData.setType("Sell");
		//Date date = new Date(System.currentTimeMillis());
		transactionData.setShares(Double.valueOf(count));
		Price stockPrice = new Price();
		stockPrice.addMoney(new Money(Double.valueOf(price), "USD"));
		transactionData.setPrice(stockPrice);
		transaction.setTransactionData(transactionData);
		String transactionFeed = portfolio.getPortfolioEntry().getFeedLink().getHref();
		transactionFeed += "/" + symbol + "/transactions";
		
		FinanceService portfolioService = new FinanceService("StockWatchService");
		GoogleAccountConnector gac = new GoogleAccountConnector();
		String authenticationToken = gac.getAuthToken("tttemper888@gmail.com", "tempster");
		portfolioService.setUserToken(authenticationToken);
		
		try 
		{
			System.out.println(transactionFeed);
			transaction = portfolioService.insert(new URL(transactionFeed), transaction);
			if(transaction == null)
			{
				System.out.println("Could not complete transaction.");
			}
			else
			{
				System.out.println("Transaction completed successfully.");
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
			try {
				transaction = portfolioService.insert(new URL(transactionFeed), transaction);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(transaction == null)
			{
				System.out.println("Could not complete transaction.");
			}
			else
			{
				System.out.println("Transaction completed successfully.");
			}
		}
		Intent intent = new Intent(this, PortfolioList.class);
		startActivity(intent);
	}
}
