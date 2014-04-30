package charting;

import java.util.ArrayList;

import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
import charting.attributes.XAxis;

public class ChartConfiguration
{
	private boolean isOrderASC;
	private int sortingAttributeIndex;
	private ArrayList<RatingAttr> ratingAttributes;
	private ArrayList<UserAttr> userAttributes;
	private ArrayList<MovieAttr> movieAttributes;
	private XAxis xAxis;
	
	public ChartConfiguration()
	{
		xAxis = XAxis.Rating;
		isOrderASC = true;
		sortingAttributeIndex = 0;
		ratingAttributes = new ArrayList<RatingAttr>();
		ratingAttributes.add(RatingAttr.Engagement);
	}
	
	public void setXAxis(XAxis xAxis)
	{
		this.xAxis = xAxis;
	}
	
	public XAxis getXAxis()
	{
		return xAxis;
	}
	
	
	public int getSortingAttrIndex()
	{
		return sortingAttributeIndex;
	}

	
	public void setSortingAttrIndex(int attrIdx)
	{
		sortingAttributeIndex = attrIdx;
	}
	
	public boolean isSortingASC()
	{
		return isOrderASC;
	}

	public void setOrderASC(boolean isOrderASC)
	{
		 this.isOrderASC = isOrderASC;
	}
	
	public void setAttributes()
	{
		
	}
	
	public Attribute[] getAttributes()
	{
		switch(xAxis)
		{
			case Rating: 	return ratingAttributes.toArray(new Attribute[ratingAttributes.size()]);							
			case User: 		return userAttributes.toArray(new Attribute[userAttributes.size()]);							
			case Movie: 	return movieAttributes.toArray(new Attribute[movieAttributes.size()]);
		}
		
		return null;
	}

	public void setRatingAttributes(ArrayList<RatingAttr> ratingAttributes)
	{
		this.ratingAttributes = ratingAttributes;
	}

	public void setUserAttributes(ArrayList<UserAttr> userAttributes)
	{
		this.userAttributes = userAttributes;
	}

	public void setMovieAttributes(ArrayList<MovieAttr> movieAttributes)
	{
		this.movieAttributes = movieAttributes;
	}
}
