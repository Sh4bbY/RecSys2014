package model;

import helper.QuickSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.Analysis;

import org.jfree.data.xy.XYSeries;

import charting.ChartConfiguration;
import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;

public class DataStructure
{
	private ArrayList<Rating> ratings;
    private HashMap<String,ArrayList<Rating>> users, movies;
    private HashMap<String, ImdbData> imdbData;
    
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
		int[] attributeIndices = config.getSelectedAttributes();
		Attribute[] attributes = null;
		
		switch(config.getXAxis())
		{
			case Rating:	attributes = RatingAttr.values();break;
			case User:		attributes = UserAttr.values();break;
			case Movie:		attributes = MovieAttr.values();break;
		}
		
		XYSeries[] series = new XYSeries[attributeIndices.length];
		QuickSort qSort = new QuickSort();
		
		List<double[]> values = getValues(config);
		
		double[][] valuesArr = new double[values.size()][attributeIndices.length];
		
		qSort.sort(values.toArray(valuesArr), config.getSortingAttrIndex(), config.isSortingASC());
		
		
		
		for(int i=0; i<attributeIndices.length; i++)
		{
			series[i] = new XYSeries(attributes[attributeIndices[i]].toString());
		}
		
		for(int i=0;i<valuesArr.length;i++)
		{
			for(int o=0;o<attributeIndices.length; o++)
			{
				series[o].add(i, valuesArr[i][o]);
			}
		}

		Analysis.logger.info(values.size() + " Elements to plot (after filtering)");
		
		return series;
	}
	
	private List<double[]> getValues(ChartConfiguration config)
	{
		int[] attributeIndices = config.getSelectedAttributes();
		List<double[]> values = new ArrayList<double[]>();
		ArrayList<Rating> tmp;
		Iterator<String> iterator;
		Rating rating;
		int attrIndex;
		double value;
		double[] valueArr;
		boolean filtered;
		
		switch(config.getXAxis())
		{
			case Rating:
				
				RatingAttr[] ratingAttributes = RatingAttr.values();
				Float tmpFloat;
				
				for(int i=0; i < ratings.size(); i++)
				{
					filtered = false;
					rating = ratings.get(i);
					valueArr = new double[attributeIndices.length];
					
					for(int o=0;o<attributeIndices.length;o++)
					{
						attrIndex = attributeIndices[o];
						switch(ratingAttributes[attrIndex])
						{
							case ImdbRating: 		tmpFloat = imdbData.get(rating.getImdbId()).getRating();
													value = (tmpFloat == null)?0:new Double(tmpFloat);
													break;
													
							case RatingDifference: 	tmpFloat = imdbData.get(rating.getImdbId()).getRating();
													value = (tmpFloat == null)?0:tmpFloat - rating.getValue(RatingAttr.Rating);
													break;
													
							default: 				value = rating.getValue(ratingAttributes[attrIndex]);break;
						}
						valueArr[o] = value;

						if(config.checkFilter(value, attrIndex))
						{
							filtered = true;
						}					
					}
					
					if(!filtered)
					{
						values.add(valueArr);
					}				
				}
				break;
				
			case User:

				UserAttr[] userAttributes = UserAttr.values();
				iterator = users.keySet().iterator();
				
				while(iterator.hasNext())
				{
					filtered = false;
					tmp = users.get(iterator.next());
					valueArr = new double[attributeIndices.length];
					
					for(int o=0;o<attributeIndices.length;o++)
					{
						attrIndex = attributeIndices[o];
						value = getUserValue(tmp,userAttributes[attrIndex]);
						valueArr[o] = value;

						if(config.checkFilter(value, attrIndex))
						{
							filtered = true;
						}
					}
					
					if(!filtered)
					{
						values.add(valueArr);
					}
				}
				break;
				
			case Movie:

				MovieAttr[] movieAttributes = MovieAttr.values();
				iterator = movies.keySet().iterator();
				String imdbID;
				
				while(iterator.hasNext())
				{
					filtered = false;
					imdbID = iterator.next();
					tmp = movies.get(imdbID);
					valueArr = new double[attributeIndices.length];
					
					for(int o=0;o<attributeIndices.length;o++)
					{
						attrIndex = attributeIndices[o];
						value = getMovieValue(tmp,movieAttributes[attrIndex],imdbID);
						valueArr[o] = value;

						if(config.checkFilter(value, attrIndex))
						{
							filtered = true;
						}
					}
					
					if(!filtered)
					{
						values.add(valueArr);
					}
				}
				break;
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
	
	private double getMovieValue(ArrayList<Rating> ratings, MovieAttr attr, String imdbID)
	{
		Double value = new Double(0);
		Integer tmpInteger;
		Float tmpFloat;
		
		switch(attr)
		{
			case AmountOfTweets: 	value = new Double(ratings.size());
									break;
									
			case Engagement:		for(Rating rating : ratings)
									{
										value += rating.getValue(RatingAttr.Engagement);
									}
									break;

			case Year:				tmpInteger = imdbData.get(imdbID).getYear();
									value = (tmpInteger == null)?new Double(-5):new Double(tmpInteger);
									break;

			case RatingCount:		tmpInteger = imdbData.get(imdbID).getRatingCount();
									value = (tmpInteger == null)?new Double(-5):new Double(tmpInteger);
									break;

			case ImdbRating:		tmpFloat = imdbData.get(imdbID).getRating();
									value = (tmpFloat == null)?new Double(-5):new Double(tmpFloat);
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
        	movies.put(rating.getImdbId(), movieRatings);
    	}
    }
    
    public Set<String> getImdbIds()
    {
    	return movies.keySet();
    }
    
    @Override
    public String toString()
    {
    	return "Rating Entries: "+ratings.size()+"   User Entries: "+users.size()+"   Movie Entries: "+movies.size();
    }

	public void setImdbData(HashMap<String, ImdbData> imdbData)
	{
		this.imdbData = imdbData;
	}
}
