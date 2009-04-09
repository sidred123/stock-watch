package com.parser;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class HistoricalDataReader 
{
	public static void main(String[] args) throws IOException
	{
		URLConnection connection = null;
		InputStream in = null;
		OutputStream out = new BufferedOutputStream(new FileOutputStream("temp.txt"));
		
		URL url = new URL("http://www.google.com/finance/historical?q=MSFT&startdate=Jan%203,%201970&enddate=May+10%2C+2007&output=csv");
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
		System.out.println(numWritten);
	}
}
