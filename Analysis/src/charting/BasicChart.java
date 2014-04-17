package charting;


import java.awt.Color;

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
	private int ratingsOutOfRange = 0;
	   
    public BasicChart() 
    {    	
    	dataset = new XYSeriesCollection();
    	dotRenderer = new XYDotRenderer();
    	
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
    
    public void addData(int x, Rating rating)
    {    		
    	for(XYSeries series : this.series)
    	{
    		String name = (String)series.getKey();
    		
    		if(name.equals("rating") && rating.getValue(name) > 10 || rating.getValue(name) < 0)
    		{
    			ratingsOutOfRange++;
    			continue;
    		}
    		
    		series.add(x, rating.getValue(name));
    	}
    	
    	//s_favoriteCount.add(x, rating.getFavoriteCount());
    }
    
    public ChartPanel createChart()
    {
    	System.out.println("ratings out of Range: "+ratingsOutOfRange);
    	dataset.removeAllSeries();
    	
    	for(XYSeries series : this.series)
    	{
    		dataset.addSeries(series);
    	}
    	
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
