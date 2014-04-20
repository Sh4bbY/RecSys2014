package model;

import java.io.Serializable;
import com.google.gson.Gson;

public class Rating implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private String twitterUserId, imdbMovieId;
	private Tweet tweet;
	private int rating, scrapingTime;
	private static Gson gson = new Gson();
	private int predictedRetweets, predictedFavorites;
	private ImdbData imdbData;

	public Rating(String twitterUserId, String imdbMovieId, String rating, String scrapingTime, String jsonTweet)
	{
		this.twitterUserId = twitterUserId;
		this.imdbMovieId = imdbMovieId;
		
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
	
	public String getResult()
	{
		return tweet.getUser().getId()+","+tweet.getTweetId()+","+predictedRetweets + predictedFavorites;
	}
	
	public String getTwitterUserId()
	{
		return twitterUserId;
	}
	
	public int getOnlineTime()
	{
		long created =  tweet.getCreatedAt().getTime()/1000;
		int days = (int)(scrapingTime - created) / (60*60*24);
		
		return days; 
	}
	
	public Integer getValue(String key)
	{
		switch(key)
		{
			case "engagement": 		return tweet.getRetweetCount() + tweet.getFavoriteCount();
			case "retweet_count": 	return tweet.getRetweetCount();
			case "favourite_count": return tweet.getFavoriteCount();
			case "rating": 			return rating;
			case "friends_count": 	return tweet.getUser().getFriendsCount();
			case "followers_count": return tweet.getUser().getFollowersCount();
			case "online_time": 	return getOnlineTime();
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
