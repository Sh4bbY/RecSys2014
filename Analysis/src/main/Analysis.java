package main;

import helper.DataManager;

import java.io.File;

import charting.DataChart;
import charting.ChartConfiguration;
import view.AnalizeFrame;
import model.DataStructure;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Analysis
{	
	public static final String[] ATTRIBUTES = {"engagement","retweet_count","favourite_count","rating","online_time", "friends_count", "followers_count"};
	
	private DataManager dataManager;
	private DataStructure dataStructure;
	private ChartConfiguration config;
	
	private AnalizeFrame frame;
	private DataChart chart;
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
        chart			= new DataChart();
	}
	

	public void readData(String fileName)
	{	
		String fileLocation = new File("").getAbsolutePath() + fileName;

		logger.info("read data: "+fileLocation);
		
		dataStructure = dataManager.readData(fileLocation);
	}
	
	public void drawChart()
	{
		chart.setData(dataStructure, config);
		
        frame.addChart(chart.createChart());
	}
}
