package model;

import java.io.Serializable;

import com.google.gson.Gson;

public class Rating implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private String twitterUserId, imdbMovieId;
	private Tweet tweet;
	private int rating, scrapingTime;
	private Result result;
	private static Gson gson = new Gson();
	private ImdbData imdbData;

	public Rating(String userId, String itemId, String rating, String scrapingTime, String jsonTweet)
	{
		this.twitterUserId = userId;
		this.imdbMovieId = itemId;
		
		this.rating = (int)Long.parseLong(rating);
		this.scrapingTime = Integer.parseInt(scrapingTime);
		
		this.tweet = gson.fromJson(jsonTweet, Tweet.class);
	}

	
	public ImdbData getImdbData()
	{
		return imdbData;
	}

	public void setImdbData(ImdbData imdbData)
	{
		this.imdbData = imdbData;
	}
	
	public String getTweetId()
	{
		return tweet.getTweetId();
	}
	
	public int getFriendCount()
	{
		return tweet.getUser().getFriendsCount();
	}
	
	public int getFavoriteCount()
	{
		return tweet.getFavoriteCount();
	}

	public int getRetweetCount()
	{
		return tweet.getRetweetCount();
	}
	public String getImdbId()
	{
		return imdbMovieId;
	}
	
	public int getRating()
	{		
		return rating;
	}
	
	public Tweet getTweet()
	{
		return tweet;
	}
	
	public Integer getValue(String key)
	{
		switch(key)
		{
			case "engagement": 		return tweet.getRetweetCount() + tweet.getFavoriteCount();
			case "retweet_count": 	return tweet.getRetweetCount();
			case "favourite_count": return tweet.getFavoriteCount();
			case "rating": 			return rating;
			case "scraping_time": 	return scrapingTime;
			case "friends_count": 	return tweet.getUser().getFriendsCount();
			case "followers_count": return tweet.getUser().getFollowersCount();
		}
		
		System.out.println("key not found");
		
		return 0;
	}
	
	@Override
	public String toString()
	{
		return "Rating - Twitter-User:" + twitterUserId + " Movie: " + imdbMovieId + "Rating: " + rating + " Tweet: " + tweet.getTweetId();
	}
}
