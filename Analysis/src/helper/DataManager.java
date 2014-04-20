package helper;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import main.Analysis;
import model.Rating;

public class DataManager
{	
	HashMap<String, ArrayList<Rating>> userMap;
	
	public HashMap<String, ArrayList<Rating>> getUserMap()
	{
		return userMap;
	}
	
	public ArrayList<Rating> readData(String fileName)
	{
		userMap = new HashMap<String, ArrayList<Rating>>();
		
		boolean first = true;
		ArrayList<Rating> ratings = new ArrayList<Rating>();
		ArrayList<Rating> userRatings;
		Rating rating;
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
	        String line;
	        
	        while((line = br.readLine()) != null) 
	        {
	        	if(first)
	        	{
	        		first = false;
	        		continue;
	        	}

	        	rating = parseData(line);
	        	ratings.add(rating);
	        	
	        	if(userMap.containsKey(rating.getTwitterUserId()))
	        	{
	        		userMap.get(rating.getTwitterUserId()).add(rating);
	        	}
	        	else
	        	{
	        		userRatings = new ArrayList<Rating>();
	        		userRatings.add(rating);
		        	userMap.put(rating.getTwitterUserId(), userRatings);
	        	}
	        }	
	        
	        br.close();

			Analysis.logger.info(" - " + ratings.size() + " Ratings parsed.");
	        return ratings;
		}
		catch(IOException e)
		{
			Analysis.logger.error(e.getMessage());
		}
		return null;
	}
	
	public void writeResults(String fileName, ArrayList<Rating> ratings)
	{	
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
	        
	        for(Rating rating : ratings) 
	        {
	        	bw.write(rating.getResult());
	        }
	        
	        bw.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	
	private Rating parseData(String line)
	{
		int idx = line.indexOf('{');
		
		String userId, itemId, rating, scraptingTime, jsonTweet;
		String[] tmp;
		
		tmp = line.substring(0, idx).split(",");
		userId = tmp[0];
		itemId = tmp[1];
		rating = tmp[2];
		scraptingTime = tmp[3];
		
		jsonTweet = line.substring(idx);
		
		return new Rating(userId, itemId, rating, scraptingTime, jsonTweet);
	}
	
	public static void storeToFile(String fileName, Object object)
	{
		try
		{  
		    FileOutputStream fos = new FileOutputStream(fileName);
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	
	    	oos.writeObject(object);
	
	    	oos.close();
	    	System.out.println("data stored to file '"+fileName+"'");
	    }
	    catch(Exception exc)
	    {
	    	exc.printStackTrace();
	    }
	}
	
	public static Object loadFromFile(String fileName)
	{
		Object obj;
		
		try
		{  
			File file = new File(fileName);
		    FileInputStream fis = new FileInputStream(file);
	    	ObjectInputStream ois = new ObjectInputStream(fis);
	
	    	obj = ois.readObject();
	    	
	    	ois.close();
	    	fis.close();
	    	System.out.println("data loaded from file '"+fileName+"'");
	    	return obj;
	    }
	    catch(Exception exc)
	    {
	    	exc.printStackTrace();
	    	return null;
	    }
	}
}
