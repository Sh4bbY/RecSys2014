package main;

import helper.DataManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import charting.BasicChart;
import view.AnalizeFrame;
import model.Rating;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Analysis
{	
	public static final String[] ATTRIBUTES = {"engagement","retweet_count","favourite_count","rating","online_time", "friends_count", "followers_count"};
	
	private DataManager dataManager;
	private ArrayList<Rating> ratings;
	private AnalizeFrame frame;
	private BasicChart chart;
	public static final Logger logger = LogManager.getLogger(Analysis.class.getName());
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Analysis analysis = new Analysis();
	}
	
	public Analysis()
	{
		frame 			= new AnalizeFrame(this);
		dataManager 	= new DataManager();
        chart			= new BasicChart();
	}
	

	public void readData(String fileName)
	{	
		String filePath = new File("").getAbsolutePath() + fileName;

		logger.info("read data: "+filePath);
		ratings = dataManager.readData(filePath);
	}
	
	public void drawChart(String[] series)
	{
        chart.setSeries(series);
        
        for(int i=0; i < ratings.size(); i++)
        {
        	chart.addData(i, ratings.get(i));
        }
        
        HashMap<String,ArrayList<Rating>> userMap = dataManager.getUserMap();
        int i=0;
        for(String userId : userMap.keySet())
        {
        	chart.addUserData(i++, userMap.get(userId));
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
