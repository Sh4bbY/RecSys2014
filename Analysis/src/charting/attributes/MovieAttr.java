package charting.attributes;

public enum MovieAttr implements Attribute
{
	Engagement, 
	AmountOfTweets,
	RatingCount,
	Year,
	ImdbRating;

	public static int getAttributeIndex(MovieAttr attribute)
	{
		MovieAttr[] values = MovieAttr.values();
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