package charting;

import java.io.Serializable;

import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;

public class ChartFilter implements Serializable
{ 
	private static final long serialVersionUID = 1L;

	public static final int LT = 0, LTE = 1, E = 2, GTE = 3, GT = 4;
	
	private double value;
	private int operator;
	private int attrIndex;

	public ChartFilter(double value, int operator, int attrIndex)
	{
		this.value = value;
		this.operator = operator;
		this.attrIndex = attrIndex;		
	}
	
	public ChartFilter(double value, int operator, Attribute attr)
	{
		this.value = value;
		this.operator = operator;
		
		if(attr instanceof RatingAttr)
		{
			this.attrIndex = RatingAttr.getAttributeIndex((RatingAttr)attr);
		}
		else if(attr instanceof UserAttr)
		{
			this.attrIndex = UserAttr.getAttributeIndex((UserAttr)attr);
		}
		else if(attr instanceof MovieAttr)
		{
			this.attrIndex = MovieAttr.getAttributeIndex((MovieAttr)attr);
		}
	}
	
	public double getValue()
	{
		return value;
	}
	
	public int getOperator()
	{
		return operator;
	}

	public int getAttrIndex()
	{
		return attrIndex;
	}
}
