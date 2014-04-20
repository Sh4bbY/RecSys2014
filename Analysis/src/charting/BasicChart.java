package charting;


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.Rating;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BasicChart extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	
	private XYPlot plot;
	
	private XYSeries[] series;
	
	private XYSeriesCollection dataset;
	private XYDotRenderer dotRenderer;
	private XYSeries userSeries;
	   
    public BasicChart() 
    {    	
    	dataset = new XYSeriesCollection();
    	dotRenderer = new XYDotRenderer();
    	userSeries = new XYSeries("userData");
    	
    	initialize();
    }
    
    private void initialize()
    {
    	dotRenderer.setDotHeight(1);
    	dotRenderer.setDotWidth(1);	
    }
    
    public void setSeries(String[] seriesNames)
    {
    	series = new XYSeries[seriesNames.length];
    	for(int i=0; i < seriesNames.length; i++)
    	{
    		series[i] = new XYSeries(seriesNames[i]);
    	}
    }
    

    public void addUserData(int x, ArrayList<Rating> ratings)
    {    		
    	userSeries.add(x, ratings.size());
    }
    
    public void addData(int x, Rating rating)
    {    		
    	for(XYSeries series : this.series)
    	{
    		String name = (String)series.getKey();
    		
    		if(name.equals("rating") && rating.getValue(name) > 10 || rating.getValue(name) < 0)
    		{
    			continue;
    		}
    		
    		series.add(x, rating.getValue(name));
    	}
    }
    
    public ChartPanel createChart()
    {
    	dataset.removeAllSeries();
    	
    	for(XYSeries series : this.series)
    	{
    		dataset.addSeries(series);
    	}
    	
    	dataset.addSeries(userSeries);
    	
    	final JFreeChart chart = ChartFactory.createXYLineChart(
                "Twitter Engagement",       // chart title
                "x",               			// domain axis label
                "y",                 		// range axis label
                dataset,                  	// data
                PlotOrientation.VERTICAL,
                true,                     	// include legend
                true,
                false
            );

    	plot = chart.getXYPlot();
    	
        final NumberAxis domainAxis = new NumberAxis("tweet");
        final NumberAxis rangeAxis = new NumberAxis("value");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);

        chart.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);
        
    	return new ChartPanel(chart);    	
    }
}
