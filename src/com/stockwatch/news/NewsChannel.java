// This part of the code is based on the code given at the following URL:
//http://learningandroid.org/index.php?q=tutorial/2009/02/writing-sax-based-rss-and-podcast-parser#

package com.stockwatch.news;

import java.util.ArrayList;
import java.util.List;

public class NewsChannel 
{
	public NewsChannel() 
	{
        setCategories(new ArrayList<String>());
        setItems(new ArrayList<NewsItem>());
	}

	public void setId(int id) 
	{
        m_Id = id;
	}

	public int getId() 
	{
        return m_Id;
	}

	public void setTitle(String title) 
	{
        m_Title = title;
	}

	public String getTitle() 
	{
	        return m_Title;
	}
	
	public void setLink(String link) 
	{
	        m_Link = link;
	}
	
	public String getLink() 
	{
	        return m_Link;
	}
	
	public void setDescription(String description) 
	{
	        m_Description = description;
	}
	
	public String getDescription() 
	{
	        return m_Description;
	}
	
	public void setPubDate(long pubDate) 
	{
	        m_PubDate = pubDate;
	}
	
	public long getPubDate() 
	{
	        return m_PubDate;
	}
	
	public void setLastBuildDate(long lastBuildDate) 
	{
	        m_LastBuildDate = lastBuildDate;
	}
	
	public long getLastBuildDate() 
	{
	        return m_LastBuildDate;
	}
	
	public void setCategories(List<String> categories) 
	{
	        m_Categories = categories;
	}
	
	public void addCategory(String category) 
	{
	        m_Categories.add(category);
	}
	
	public List<String> getCategories() 
	{
	        return m_Categories;
	}
	
	public void setItems(List<NewsItem> items) 
	{
	        m_Items = items;
	}
	
	public void addItem(NewsItem item)
	{
	        m_Items.add(item);
	}
	
	public List<NewsItem> getItems() 
	{
	        return m_Items;
	}
	
	public void setImage(String image) 
	{
	        m_Image = image;
	}
	
	public String getImage() 
	{
	        return m_Image;
	}
	
	private int m_Id;
	private String m_Title;
	private String m_Link;
	private String m_Description;
	private long m_PubDate;
	private long m_LastBuildDate;
	private List<String> m_Categories;
	private List<NewsItem> m_Items;
	private String m_Image;
}
