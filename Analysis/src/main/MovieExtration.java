package main;
import helper.DataManager;
import helper.ImdbExtractor;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.ImdbData;


public class MovieExtration
{
	private static final String IMDB_FILE_NAME = "imdbData.dat";
	
	private DataManager dataManager;
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
		String filePath;
		
		filePath = new File("").getAbsolutePath()+"/assets/test.dat";
		Set<String> imdbIdSet1 = dataManager.readData(new File(filePath)).getImdbIds();
		System.out.println("loaded data from '"+filePath+"'");

		filePath = new File("").getAbsolutePath()+"/assets/training.dat";
		Set<String> imdbIdSet2 = dataManager.readData(new File(filePath)).getImdbIds();
		System.out.println("loaded data from '"+filePath+"'");
		
		Set<String> imdbIdSet = new HashSet<String>();

		imdbIdSet.addAll(imdbIdSet1);	
		imdbIdSet.addAll(imdbIdSet2);		
		String[] imdbIDs = imdbIdSet.toArray(new String[imdbIdSet.size()]);
		System.out.println("concat ImdbIDs");
		
				
		imdbData = (HashMap<String, ImdbData>)DataManager.loadFromFile(IMDB_FILE_NAME);
		
		if(imdbData == null)
		{
			imdbData = new HashMap<String, ImdbData>();
		}		
		
		int threadAmount = 12;
		
		Thread[] threads = new Thread[threadAmount];
		int ratingIdx;
		
		for(int i=0; i<threadAmount; i++)
		{
			ratingIdx = Math.round((imdbIDs.length/ threadAmount) * i);
			threads[i] = new Thread(new ImdbExtractor(imdbIDs, imdbData, ratingIdx, i));
			threads[i].start();
		}
	}
}
