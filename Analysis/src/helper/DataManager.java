package helper;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import main.Analysis;
import model.DataStructure;
import model.Rating;

public class DataManager
{	
	HashMap<String, ArrayList<Rating>> userMap;
	
	public HashMap<String, ArrayList<Rating>> getUserMap()
	{
		return userMap;
	}
	
	public static DataStructure readData(File file)
	{
		DataStructure dataStructure = new DataStructure();
		
		boolean firstLine = true;
		Rating rating;
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
	        String line;
	        
	        while((line = br.readLine()) != null) 
	        {
	        	if(firstLine)
	        	{
	        		firstLine = false;
	        		continue;
	        	}

	        	rating = parseData(line);
	        	dataStructure.addData(rating);
	        }	
	        
	        br.close();

			Analysis.logger.info("readData finished.");
			Analysis.logger.info(dataStructure);
			
	        return dataStructure;
		}
		catch(IOException e)
		{
			Analysis.logger.error(e.getMessage());
		}
		
		return null;
	}
	
	public static void writeSolution(String fileName, ArrayList<Rating> ratings)
	{	
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
	        
			bw.write("userid,tweetid,engagement\n");
			
			Collections.sort(ratings, new Comparator<Rating>()
			{
				@Override
				public int compare(Rating r1, Rating r2)
				{
					Long v1 = Long.parseLong(r1.getTwitterUserId()),
						 v2 = Long.parseLong(r2.getTwitterUserId());
					int result = v2.compareTo(v1);
					
					if(result == 0)
					{
						return r2.getTweetId().compareTo(r1.getTweetId());
					}
					return result;
				}
			});
			
	        for(Rating rating : ratings)
	        {
	        	bw.write(rating.getSolution()+"\n");
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

	
	private static Rating parseData(String line)
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
	
	synchronized public static void storeToFile(String fileName, Object object)
	{
		try
		{  
		    FileOutputStream fos = new FileOutputStream(fileName);
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	
	    	oos.writeObject(object);
	
	    	oos.close();
	    	System.out.println("data stored to file '"+fileName+"'");
	    }
		catch(ConcurrentModificationException ex)
		{
			System.err.println("ConcurrentModificationException on storing file: '"+fileName+"'");
		}
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
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
		catch(EOFException ex)
		{
			System.err.println("couldn't load '"+fileName+"'. File is corrupt");
		}
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
    	return null;
	}
}
