package charting;


import java.awt.Color;

import javax.swing.JPanel;

import model.DataStructure;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataChartFactory extends JPanel
{
	private static final long	serialVersionUID	= 1L;	
	private XYDotRenderer dotRenderer;
	   
    public DataChartFactory() 
    {    	
    	dotRenderer = new XYDotRenderer();
    	
    	initialize();
    }
    
    private void initialize()
    {
    	dotRenderer.setDotHeight(1);
    	dotRenderer.setDotWidth(1);	
    }
    
    public ChartPanel createChart(DataStructure data, ChartConfiguration config)
    {
    	XYSeries[] series 			= data.getSeries(config);
    	XYSeriesCollection dataset 	= new XYSeriesCollection();
    	
    	for(XYSeries serie : series)
    	{
    		dataset.addSeries(serie);
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

    	XYPlot plot = chart.getXYPlot();
    	
        final NumberAxis domainAxis = new NumberAxis("tweet");
        final NumberAxis rangeAxis = new NumberAxis("value");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);

        chart.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);
        
    	return new ChartPanel(chart);    	
    }
}
