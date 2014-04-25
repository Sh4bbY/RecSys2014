package model;

import java.util.ArrayList;
import java.util.HashMap;

import main.Analysis;

public class DataStructure
{
	private HashMap<String,Rating> ratings;
    private HashMap<String,ArrayList<Rating>> users, movies;
    
    public DataStructure()
    {
    	ratings = new HashMap<String,Rating>();
    	users 	= new HashMap<String,ArrayList<Rating>>();
    	movies 	= new HashMap<String,ArrayList<Rating>>();
    }
    
    public void addData(Rating rating)
    {
    	addRating(rating);
    	addUser(rating);
    	addMovie(rating);
    }
    
    private void addRating(Rating rating)
    {
    	if(ratings.containsKey(rating.getTweetId()))
    	{
    		Analysis.logger.warn("duplicate Entry for tweetId: "+rating.getTweetId());
    		return;
    	}
    	
    	ratings.put(rating.getTweetId(), rating);
    }

    private void addUser(Rating rating)
    {
    	if(users.containsKey(rating.getTwitterUserId()))
    	{
    		users.get(rating.getTwitterUserId()).add(rating);
    	}
    	else
    	{
    		ArrayList<Rating> userRatings = new ArrayList<Rating>();
    		userRatings.add(rating);
        	users.put(rating.getTwitterUserId(), userRatings);
    	}
    }
    
    private void addMovie(Rating rating)
    {
    	if(movies.containsKey(rating.getImdbId()))
    	{
    		movies.get(rating.getImdbId()).add(rating);
    	}
    	else
    	{
    		ArrayList<Rating> movieRatings = new ArrayList<Rating>();
    		movieRatings.add(rating);
        	users.put(rating.getImdbId(), movieRatings);
    	}
    }
    
    @Override
    public String toString()
    {
    	return "Rating Entries: "+ratings.size()+" User Entries: "+users.size()+"Movie Entries: "+movies.size();
    }
}
