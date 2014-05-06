package charting.attributes;

public enum RatingAttr implements Attribute
{
	Engagement,
	FavoriteCount,
	RetweetCount,
	OnlineTime,
	FriendCount,
	FollowerCount,
	Rating,
	ImdbRating,
	RatingDifference;

	public static int getAttributeIndex(RatingAttr attribute)
	{
		RatingAttr[] values = RatingAttr.values();
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