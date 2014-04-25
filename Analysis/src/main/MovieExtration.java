package main;
import helper.DataManager;
import helper.ImdbExtractor;

import java.util.ArrayList;
import java.util.HashMap;

import model.ImdbData;
import model.Rating;


public class MovieExtration
{
	private static final String IMDB_FILE_NAME = "imdbData.dat";
	
	private DataManager dataManager;
	private ArrayList<Rating> ratings;
	private HashMap<String, ImdbData> imdbData;
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		MovieExtration extraction = new MovieExtration();
	}
	
	@SuppressWarnings("unchecked")
	public MovieExtration()
	{
		dataManager = new DataManager();
		//ratings = dataManager.readData("F:\\Projects\\RecSys2014\\Analysis\\src\\assets\\training.dat");
		
		System.out.println(ratings.size() + " ratings parsed");
		
		imdbData = (HashMap<String, ImdbData>)DataManager.loadFromFile(IMDB_FILE_NAME);
		
		
		int threadAmount = 0;
		
		Thread[] threads = new Thread[threadAmount];
		int ratingIdx;
		for(int i=0; i<threadAmount; i++)
		{
			ratingIdx = Math.round(((ratings.size())/ threadAmount) * i);
			threads[i] = new Thread(new ImdbExtractor(ratingIdx, ratings, imdbData));
			threads[i].start();
		}
	}
}
