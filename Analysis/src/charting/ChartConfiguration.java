package charting;

import charting.attributes.XAxis;

public class ChartConfiguration
{	
	private boolean isOrderASC;
	private int sortingAttributeIndex;
	private int[][] selectedAttributes;
	
	
	private XAxis xAxis;
	private ChartFilter[] chartFilters;
	
	public ChartConfiguration()
	{
		xAxis = XAxis.Rating;
		isOrderASC = true;
		sortingAttributeIndex = 0;
		selectedAttributes = new int[3][];
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
	
	public void setFilters(ChartFilter[] chartFilters)
	{
		this.chartFilters = chartFilters;
	}
	
	public void setSelectedAttributes(int[][] selectedAttributes)
	{
		this.selectedAttributes = selectedAttributes;
	}

	public int[] getSelectedAttributes()
	{
		XAxis[] axis = XAxis.values();
		
		for(int i=0;i<axis.length;i++)
		{
			if(axis[i].equals(xAxis))
			{
				return selectedAttributes[i];
			}
		}
		
		return null;
	}
	
	public boolean checkFilter(double value, int attrIndex)
	{
		for(ChartFilter filter : chartFilters)
		{
			if(attrIndex == filter.getAttrIndex())
			{
				switch(filter.getOperator())
				{
					case ChartFilter.LT: 	if(!(value < filter.getValue())) return true; break;
					case ChartFilter.LTE: 	if(!(value <= filter.getValue())) return true; break;
					case ChartFilter.E: 	if(!(value == filter.getValue())) return true; break;
					case ChartFilter.GTE: 	if(!(value >= filter.getValue())) return true; break;
					case ChartFilter.GT: 	if(!(value > filter.getValue())) return true; break;
				}
			}
		}
		
		return false;
	}
}
