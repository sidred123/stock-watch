package com.stockwatch;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PortfolioList extends Activity implements OnClickListener
{
	private Hashtable idHash = new Hashtable();
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final TableLayout table = (TableLayout)findViewById(R.id.table1);
        PortfolioManager portfolioManager = PortfolioManager.getPortfolioManager("tttemper888@gmail.com", "tempster");
        List<StockWatchPortfolio> portfolioList = portfolioManager.getPortfolios();
        Iterator<StockWatchPortfolio> portfolioIterator = portfolioList.iterator();
		int idCount = 1;
		TableRow titleRow = new TableRow(this);
		titleRow.setBackgroundColor(Color.rgb(102, 0, 0));
		TextView title = new TextView(this);
		title.setText("Portfolios");
		titleRow.addView(title);
		table.addView(titleRow);
		boolean toggle = true;
        while(portfolioIterator.hasNext())
		{
			final StockWatchPortfolio portfolio = portfolioIterator.next();
			TableRow tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.rgb(102, 0, 0));
			TextView textView = new TextView(this);
			textView.setText(portfolio.getPortfolioEntry().getTitle().getPlainText());
			textView.setOnClickListener(this);
			textView.setId(idCount);
			tableRow.addView(textView);
			table.addView(tableRow);
			idHash.put(new Integer(idCount), idCount);
			final int id = idCount;
			idCount++;
			  
			if(toggle)
			{
				tableRow.setBackgroundColor(Color.argb(140,102, 0, 0));
			}
			else
			{
				tableRow.setBackgroundColor(Color.argb(90,102, 0, 0));
			}
			toggle = !toggle;
			Button buyButton = new Button(this);
			buyButton.setText("Buy");
			
			final PortfolioList screen = this;
			
			buyButton.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v) 
				{
					Intent intent = new Intent(screen, BuySell.class);
					intent.putExtra("portfolio", String.valueOf(id));
					intent.putExtra("action", 1);
					startActivity(intent);
				}
			});
			
			tableRow.addView(buyButton);
			Button sellButton = new Button(this);
			sellButton.setText("Sell");
			tableRow.addView(sellButton);
			
			sellButton.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v) 
				{
					Intent intent = new Intent(screen, BuySell.class);
					intent.putExtra("portfolio", String.valueOf(id));
					intent.putExtra("action", 0);
					startActivity(intent);
				}
			});
		}
	}

	public void onClick(View view) 
	{
		// TODO Auto-generated method stub
		Integer id = new Integer(view.getId());
		//StockWatchPortfolio portfolio = (StockWatchPortfolio)idHash.get(id);
		Intent intent = new Intent(this, PortfolioStockList.class);
		intent.putExtra("portfolio", String.valueOf(id));
		startActivity(intent);
	}
}
