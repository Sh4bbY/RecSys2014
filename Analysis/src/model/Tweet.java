package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tweet implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private String contributors;
	private Boolean truncated;
	private String text;
	private String in_reply_to_status_id;
	private String id;
	private Integer favorite_count;
	private String source;
	private Boolean retweeted;
	private String in_reply_to_screen_name;
	private String id_str;
	private Integer retweet_count;
	private String in_reply_to_user_id;
	private String favorited;
	private TweetUser user;
	private String in_reply_to_user_id_str;
	private String possibly_sensitive;
	private String lang;
	private String created_at;
	private String in_reply_to_status_id_str;
	
	/* disregarded objects
	 * 
	 *		place, geo, coordinates, entities
	 */
	
	@Override
	public String toString()
	{
		return "TweetObj id: "+id+" user: "+user.toString();
	}
	
	public String getTweetId()
	{
		return id;
	}

	public TweetUser getUser()
	{
		return user;
	}
	
	public Integer getRetweetCount()
	{
		return retweet_count;
	}
	
	public Integer getFavoriteCount()
	{
		return favorite_count;
	}
	
	public Date getCreatedAt()
	{
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
	    
		try
		{
			return df.parse(created_at);
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		} 
	}
}
