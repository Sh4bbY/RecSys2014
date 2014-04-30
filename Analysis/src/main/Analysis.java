package main;

import helper.DataManager;

import java.io.File;

import charting.DataChartFactory;
import charting.ChartConfiguration;
import view.AnalizeFrame;
import model.DataStructure;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Analysis
{	
	private DataManager dataManager;
	private DataStructure dataStructure;
	private ChartConfiguration config;
	private DataChartFactory chartFacotry;
	
	private AnalizeFrame frame;
	public static final Logger logger = LogManager.getLogger(Analysis.class.getName());
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Analysis analysis = new Analysis();
	}
	
	public Analysis()
	{
        config			= new ChartConfiguration();
		dataManager 	= new DataManager();
		frame 			= new AnalizeFrame(this);
		chartFacotry 	= new DataChartFactory();
	}
	

	public void readData(File file)
	{	
		logger.info("read data: "+file.getName());
		
		dataStructure = dataManager.readData(file);
	}
	
	public void drawChart()
	{
        frame.addChart(chartFacotry.createChart(dataStructure,config));
	}
	
	public ChartConfiguration getConfig()
	{
		return config;
	}
}
