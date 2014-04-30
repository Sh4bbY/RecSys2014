package model;

import helper.QuickSort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.jfree.data.xy.XYSeries;

import charting.ChartConfiguration;
import charting.attributes.Attribute;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
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
		QuickSort qSort = new QuickSort();
		
		double[][] values = getValues(config);
		
		qSort.sort(values, config.getSortingAttrIndex(), config.isSortingASC());
		
		for(int i=0; i<attributes.length; i++)
		{
			series[i] = new XYSeries(attributes[i].toString());
			
			for(int o=0;o<values[0].length; o++)
			{
				series[i].add(o,values[i][o]);
			}
		}
		
		return series;
	}
	
	private double[][] getValues(ChartConfiguration config)
	{
		Attribute[] attributes = config.getAttributes();
		double[][] values = null;
		ArrayList<Rating> tmp;
		Iterator<String> iterator;
		Rating rating;
		int idx;
		
		switch(config.getXAxis())
		{
			case Rating:		
				values = new double[attributes.length][ratings.size()];
				for(int i=0; i < ratings.size(); i++)
				{
					rating = ratings.get(i);
					
					for(int o=0;o<attributes.length;o++)
					{
						values[o][i] = rating.getValue((RatingAttr)attributes[o]);
					}
				}
				break;
				
			case User:
				values = new double[attributes.length][users.size()];
				iterator = users.keySet().iterator();
				idx = 0;
				while(iterator.hasNext())
				{
					tmp = users.get(iterator.next());
					
					for(int o=0;o<attributes.length;o++)
					{
						values[o][idx] = getUserValue(tmp,(UserAttr)attributes[o]);
					}
					
					idx++;
				}
				break;
			case Movie:
				for(int i=0; i < ratings.size(); i++)
				{
					for(Attribute attr : attributes)
					{
						//series[i].add(i,ratings.get(i).getValue((RatingAttr)attr));
					}
				}
		}
		
		return values;
	}
	
	private double getUserValue(ArrayList<Rating> ratings, UserAttr attr)
	{
		double value = 0;
		
		switch(attr)
		{
			case AmountOfTweets: 	value = ratings.size(); 
									break;
									
			case Engagement:		for(Rating rating : ratings)
									{
										value += rating.getValue(RatingAttr.Engagement);
									}
									break;
									
		}
		
		return value;
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
