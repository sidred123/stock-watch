package com.stockwatch;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PortfolioList extends Activity 
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TableLayout table = (TableLayout)findViewById(R.id.table1);
        PortfolioManager portfolioManager = PortfolioManager.getPortfolioManager("tttemper888@gmail.com", "tempster");
        List<StockWatchPortfolio> portfolioList = portfolioManager.getPortfolios();
        Iterator<StockWatchPortfolio> portfolioIterator = portfolioList.iterator();
		while(portfolioIterator.hasNext())
		{
			StockWatchPortfolio portfolio = portfolioIterator.next();
			TableRow tableRow = new TableRow(this);
			TextView textView = new TextView(this);
			textView.setText(portfolio.getPortfolioEntry().getTitle().getPlainText());
			tableRow.addView(textView);
			table.addView(tableRow);
		}
	}
}
