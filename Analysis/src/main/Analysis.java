package main;

import helper.DataManager;

import java.io.File;
import java.util.HashMap;

import charting.DataChartFactory;
import charting.ChartConfiguration;
import view.AnalizeFrame;
import model.DataStructure;
import model.ImdbData;

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
		
		String imdbDataPath = new File("").getAbsolutePath()+"/imdbData.dat";
		dataStructure.setImdbData((HashMap<String, ImdbData>)DataManager.loadFromFile(imdbDataPath));
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
