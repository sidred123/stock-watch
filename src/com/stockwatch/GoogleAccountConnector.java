package com.stockwatch;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class GoogleAccountConnector  
{
	static final String googleAccountURL = "https://www.google.com/accounts/ClientLogin";
	
	public static String getAuthToken(String username, String password)
	{
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("http://74.125.43.99/accounts/ClientLogin");
		method.addParameter("accountType", "GOOGLE");
		method.addParameter("Email", "tttemper888@gmail.com");
		method.addParameter("Passwd", "tempster");
		method.addParameter("service", "finance");
		method.addParameter("source", "SubObjective2");
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		try
		{
			int status = httpClient.executeMethod(method);
			System.out.println("Status:"+status);
			return extractTokenFromResponse("Auth", method.getResponseBodyAsString());
		}
		catch(HttpException hE)
		{
			hE.printStackTrace();
		}
		catch(IOException iOE)
		{
			iOE.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static String extractTokenFromResponse(String token, String response)
	{
		String result = null;
		String tokenPrefix = (new StringBuilder()).append(token).append("=").toString();
		int startIdx = response.indexOf(tokenPrefix);
		if(startIdx >= 0)
		{
			int endIdx = response.indexOf('\n', startIdx);
			if(endIdx > 0)
			{
				result = response.substring(startIdx + tokenPrefix.length(), endIdx);
			}
			else
			{
				result = response.substring(startIdx + tokenPrefix.length());
			}
		}
		return result;
	}
}
