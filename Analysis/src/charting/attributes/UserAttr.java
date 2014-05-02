package charting.attributes;

public enum UserAttr implements Attribute
{
	AmountOfTweets,
	Engagement;

	public static int getAttributeIndex(UserAttr attribute)
	{
		UserAttr[] values = UserAttr.values();
		for(int i=0; i<values.length; i++)
		{
			if(attribute.equals(values[i]))
			{
				return i;
			}
		}
		
		return -1;
	}
}