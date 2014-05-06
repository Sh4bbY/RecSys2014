package charting.attributes;

public enum XAxis 
{
	Rating,User,Movie;
	
	public static int getXAxisIndex(XAxis axis)
	{
		XAxis[] values = XAxis.values();
		
		for(int i=0; i<values.length; i++)
		{
			if(axis.equals(values[i]))
			{
				return i;
			}
		}
		return -1;
	}
}
