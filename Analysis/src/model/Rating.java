package model;

import java.io.Serializable;

import main.Analysis;
import charting.attributes.RatingAttr;

import com.google.gson.Gson;

public class Rating implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private String twitterUserId, imdbMovieId;
	private Tweet tweet;
	private int rating, scrapingTime;
	private static Gson gson = new Gson();
	private float predictedRetweetCount, predictedFavoriteCount;
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
	
	public String getSolution()
	{
		return tweet.getUser().getId()+","+tweet.getTweetId()+","+Math.round(predictedRetweetCount + predictedFavoriteCount);
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
	
	public double getValue(RatingAttr attr)
	{
		switch(attr)
		{
			case Engagement: 	return tweet.getRetweetCount() + tweet.getFavoriteCount();
			case RetweetCount: 	return tweet.getRetweetCount();
			case FavoriteCount: return tweet.getFavoriteCount();
			case Rating: 		return rating;
			case FriendCount: 	return tweet.getUser().getFriendsCount();
			case FollowerCount: return tweet.getUser().getFollowersCount();
			case OnlineTime: 	return getOnlineTime();
			default:			Analysis.logger.warn("Attribute not found");return 0;
		}
	}
	
	public void setPredictedRetweetCount(float predictedRetweetCount)
	{
		this.predictedRetweetCount = predictedRetweetCount;
	}
	
	public void setPredictedFavoriteCount(float predictedFavoriteCount)
	{
		this.predictedFavoriteCount = predictedFavoriteCount;
	}
	
	public float getPredictedRetweetCount()
	{
		return predictedRetweetCount;
	}
	
	public float getPredictedFavoriteCount()
	{
		return predictedFavoriteCount;
	}
	
	@Override
	public String toString()
	{
		return "Rating - Twitter-User:" + twitterUserId + " Movie: " + imdbMovieId + "Rating: " + rating + " Tweet: " + tweet.getTweetId();
	}
}
