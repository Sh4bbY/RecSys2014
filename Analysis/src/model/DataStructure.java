package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.jfree.data.xy.XYSeries;

import charting.ChartConfiguration;
import charting.attributes.Attribute;
import charting.attributes.RatingAttr;
import charting.attributes.XAxis;
import main.Analysis;

public class DataStructure
{
	private ArrayList<Rating> ratings;
    private HashMap<String,ArrayList<Rating>> users, movies;
    
    public DataStructure()
    {
    	ratings = new ArrayList<Rating>();
    	users 	= new HashMap<String,ArrayList<Rating>>();
    	movies 	= new HashMap<String,ArrayList<Rating>>();
    }
    
    public void addData(Rating rating)
    {
    	addRating(rating);
    	addUser(rating);
    	addMovie(rating);
    }
    
	public XYSeries[] getSeries(ChartConfiguration config)
	{
		Attribute[] attributes = config.getAttributes();
		XYSeries[] series = new XYSeries[attributes.length];
		Rating rating;
		
		for(int i=0; i<attributes.length; i++)
		{
			series[i] = new XYSeries(attributes[i].toString());
		}

		final RatingAttr sortingAttr = config.getSortingAttribute();
		final boolean isSortingASC = config.isSortingASC();
		
    	Collections.sort(ratings,new Comparator<Rating>()
		{
			@Override
			public int compare(Rating r1, Rating r2)
			{
				if(isSortingASC)
					return r1.getValue(sortingAttr).compareTo(r2.getValue(sortingAttr));
				
				return r2.getValue(sortingAttr).compareTo(r1.getValue(sortingAttr));
			}
		});
		
		
		switch(config.getXAxis())
		{
			case Rating:				
				for(int i=0; i < ratings.size(); i++)
				{
					rating = ratings.get(i);
					
					for(int o=0;o<attributes.length;o++)
					{
						series[o].add(i,rating.getValue((RatingAttr)attributes[o]));
					}
				}
				break;
				
			case User:
			case Movie:
				
				for(int i=0; i < ratings.size(); i++)
				{
					for(Attribute attr : attributes)
					{
						//series[i].add(i,ratings.get(i).getValue((RatingAttr)attr));
					}
				}
		}
		
		return series;
	}
	
    
    private void addRating(Rating rating)
    {    	
    	ratings.add(rating);
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
