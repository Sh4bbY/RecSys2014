package model;

import java.io.Serializable;

public class ImdbData implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	
	private String id;
	private String name;
	private Float rating;
	private Integer ratingCount, year;
	
	public ImdbData(String id)
	{
		this.id = id;
	}
	
	public Integer getYear()
	{
		return year;
	}
	public Float getRating()
	{
		return rating;
	}
	
	public void setRating(float rating)
	{
		this.rating = rating;
	}
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Integer getRatingCount()
	{
		return ratingCount;
	}
	public void setRatingCount(int ratingCount)
	{
		this.ratingCount = ratingCount;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	@Override
	public String toString()
	{
		return id+", "+name+", "+year+", "+rating+", "+ratingCount;
	}
}
