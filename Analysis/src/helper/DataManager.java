package helper;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import model.Rating;
import model.Result;

public class DataManager
{	
	public String readFile(String fileName)
	{
		String result = "";
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
	        String line;
	        
	        while((line = br.readLine()) != null) 
	        {
	        	result += line;
	        }	
	        
	        br.close();
	        
	        return result;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Rating> readTestData(String fileName)
	{
		boolean first = true;
		ArrayList<Rating> ratings = new ArrayList<Rating>();
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

	        	rating = parseTestData(line);
	        	ratings.add(rating);
	        }	
	        
	        br.close();
	        
	        return ratings;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, Result> readTestResults(String fileName)
	{
		boolean first = true;
		HashMap<String, Result> results = new HashMap<String, Result>();
		Result result;
		
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

	        	result = parseTestResult(line);
	        	results.put(result.getTweetId(), result);
	        }	
	        
	        br.close();
	        
	        return results;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	
	private Rating parseTestData(String line)
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
	
	private Result parseTestResult(String line)
	{		
		String userId, tweetId;
		int engagement;
		String[] tmp;
		
		tmp = line.split(",");
		userId = tmp[0];
		tweetId = tmp[1];
		engagement = Integer.parseInt(tmp[2]);
		
		return new Result(userId,tweetId,engagement);
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
