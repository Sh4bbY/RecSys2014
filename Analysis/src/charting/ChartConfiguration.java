package charting;

import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
import charting.attributes.XAxis;

public class ChartConfiguration
{
	private boolean sortingOrderASC;
	private RatingAttr ratingAttributes;
	private MovieAttr movieAttributes;
	private UserAttr userAttributes;
	private XAxis xAxis;
	
	public void setXAxis(XAxis xAxis)
	{
		this.xAxis = xAxis;
	}
	
	public void setAttributes()
	{
		
	}
	//private Filter filter;
}
