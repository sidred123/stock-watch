// This part of the code is based on the code given at the following URL:
//http://learningandroid.org/index.php?q=tutorial/2009/02/writing-sax-based-rss-and-podcast-parser#

package com.stockwatch.news;

public class NewsItem
{
	public void setId(int id) 
	{
		this._id = id;
	}
	 
	public int getId() 
	{
		return _id;
	}
	 
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getTitle() 
	{
		return this.title;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public String getDescription() 
	{
		return this.description;
	}
	
	public void setLink(String link) 
	{
		this.link = link;
	}
	
	public String getLink() 
	{
		return this.link;
	}
	
	public void setPubdate(String pubdate) 
	{
		this.pubDate = pubdate;
	}
	
	public String getPubdate() 
	{
		return this.pubDate;
	}

	private int _id;
	private String title;
	private String link;
	private String description;
	private String pubDate;
}
