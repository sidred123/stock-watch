package com.parser;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;

public class RSSParser 
{
	public static void main(String[] args) throws Exception 
	{
		RSSDigester digester = new RSSDigester();
		String feed = "http://www.google.com/finance?morenews=10&rating=1&q=NASDAQ:MSFT&output=rss";
		URL url =  new URL(feed);
		HttpURLConnection httpSource = (HttpURLConnection)url.openConnection();
		Channel channel = (Channel)digester.parse(httpSource.getInputStream());
		if(channel == null)
		{
			throw new Exception("Cant communicate with " + url);
		}
		Item rssItems[] = channel.findItems();
		for(int i = 0; i < rssItems.length; i++)
		{
			System.out.println(rssItems[i].getTitle());
			System.out.println(rssItems[i].getLink());
			System.out.println(rssItems[i].getDescription());
			System.out.println();
		}
	}
}
