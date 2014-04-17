package main;

import helper.DataManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import charting.BasicChart;
import view.AnalizeFrame;
import model.ImdbData;
import model.Rating;
import model.Result;
import model.Tweet;

public class Analysis
{	
	public static final String[] ATTRIBUTES = {"engagement","retweet_count","favourite_count","rating","scraping_time", "friends_count", "followers_count"};
	
	private DataManager dataManager;
	private ArrayList<Rating> ratings;
	private AnalizeFrame frame;
	private BasicChart chart;
	
	public static void main(String[] args)
	{
		Analysis analysis = new Analysis();
	}
	
	public Analysis()
	{
		frame 			= new AnalizeFrame(this);
		dataManager 	= new DataManager();
        chart			= new BasicChart();
        
		//ratings 		= readTestData(dataManager);
		//imdbData 		= (HashMap<String, ImdbData>)DataManager.loadFromFile(IMDB_FILE_NAME);
		
		/*for(int i = 0; i < ratings.size(); i++)
		{
			if(imdbData.containsKey(ratings.get(i).getImdbId()))
			{
				ratings.get(i).setImdbData(imdbData.get(ratings.get(i).getImdbId()));
			}
			else
			{
				System.out.println(i + " - " + ratings.get(i).getImdbId());
			}
		}*/

		//sortData();
		
	}
	

	public void readData(String fileName)
	{
		frame.setStatus("read data: "+fileName);
		String basePath = new File("").getAbsolutePath();
		
		ratings = dataManager.readTestData(basePath + fileName);
		System.out.println(" - " + ratings.size() + " Ratings parsed.");
		frame.setStatus("");
	}
	
	public void drawChart(String[] series)
	{
        chart.setSeries(series);
        
        for(int i=0; i < ratings.size(); i++)
        {
        	chart.addData(i, ratings.get(i));
        }

        frame.setChart(chart.createChart());
	}
	
	public void sortData(final String orderingAttribute, final boolean isASC)
	{
		frame.setStatus("ordering data");
		
		Collections.sort(ratings, new Comparator<Rating>() 
		{
	        @Override
	        public int compare(Rating a, Rating b)
	        {
	        	if(isASC)
	        		return a.getValue(orderingAttribute).compareTo(b.getValue(orderingAttribute));
	        		
	            return  b.getValue(orderingAttribute).compareTo(a.getValue(orderingAttribute));
	        }
	    });

		frame.setStatus("");
	}
	
	
}
