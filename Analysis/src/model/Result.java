package model;

import java.io.Serializable;

public class Result implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private String userId,tweetId;
	private int engagement;
	
	public Result(String userId, String tweetId, int engagement)
	{
		this.userId = userId;
		this.tweetId = tweetId;
		this.engagement = engagement;		
	}

	public String getTweetId()
	{
		return tweetId;
	}

	public String getUserId()
	{
		return userId;
	}

	public Integer getEngagement()
	{
		return engagement;
	}
	
	@Override
	public String toString()
	{
		return "Result - User: " + userId + ",  Tweet: " + tweetId + ", Score: " + engagement;
	}
}
