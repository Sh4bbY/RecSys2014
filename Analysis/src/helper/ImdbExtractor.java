package helper;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import model.ImdbData;
import model.Rating;

public class ImdbExtractor implements Runnable
{
	private final String baseUrl = "http://www.imdb.com/title/tt";
	
    private HTMLDocument htmlDoc;
    private String line, valueKey = null;
    private HashMap<String,ImdbData> results;
    private ImdbData imdbData;
    private ArrayList<Rating> ratings;
    private int count;
    
	public ImdbExtractor(int count, ArrayList<Rating> ratings, HashMap<String, ImdbData> results)
	{		
		this.count = count;
		this.ratings = ratings;
		this.results = results;
		this.valueKey = "";
		
		if(results == null)
		{
			this.results = new HashMap<String, ImdbData>();
		}	
		
		htmlDoc = new HTMLDocument();
	}


	@Override
	public void run()
	{
		startExtraction();
	}

        
	public boolean startExtraction()
	{		
		String id = ratings.get(count).getImdbId();
		
		if(results.containsKey(id))
		{
			count++;
	    	if(count < this.ratings.size() )
	    	{
				startExtraction();
				return true;
	    	}
	    	else
	    	{
	    		DataManager.storeToFile("imdbData.dat", results);
	    		System.out.println("------- Extracted Information: "+results.size());
	    		return true;
	    	}
		}
		
		imdbData = new ImdbData(id);
		InputStream in;
        InputStreamReader reader;
        URL url;
        
		try
		{
			url = new URL(baseUrl + id);
			in = url.openStream();
            reader = new InputStreamReader(in);
            new ParserDelegator().parse(reader, new ExtractionCallback(imdbData), true);
        }
        catch(Exception e)
        {
        	System.err.println(e.getMessage());
        	e.printStackTrace();
        	return false;
        }
		
        return true;
	}

	
	class ExtractionCallback extends ParserCallback 
	{
		private ImdbData imdbData;
		
		public ExtractionCallback(ImdbData imdbData)
		{
			super();
			this.imdbData = imdbData;
		}
		
		@Override
	    public void handleStartTag(final Tag t, final MutableAttributeSet a, final int pos) 
		{
	        switch(t.toString()) 
	        {
		    	case "a":	checkYear(a);
		    				break;
		    	case "div":	checkRating(a);
		    				break;
		    	case "span":checkSpan(a);
	        }
	    }
		
		
		@Override
	    public void handleText(char[] data, int pos) 
		{
			switch(valueKey)
			{
				case "year": imdbData.setYear(Integer.parseInt(new String(data)));break;
				case "name": imdbData.setName(new String(data));break;
				case "rating": imdbData.setRating(Float.parseFloat(new String(data)));break;
				case "ratingCount": String tmp = new String(data);
									imdbData.setRatingCount(Integer.parseInt(tmp.replace(",","")));break;
			}
			valueKey = "";
	    }	
		
		private void checkYear(MutableAttributeSet a)
		{
			if(imdbData.getYear() != null)
				return;
			
			final Object attr = a.getAttribute(HTML.Attribute.HREF);
			
	        if(attr != null) 
	        {
	            String attrStr = attr.toString();
	            
	            if(attrStr.startsWith("/year/") && attrStr.substring(10).startsWith("/?"))
	            {
	            	valueKey = "year";
	            }
	        }
		}
		
		private void checkRating(MutableAttributeSet a)
		{
			if(imdbData.getRating() != null)
				return;
			
			final Object attr = a.getAttribute(HTML.Attribute.CLASS);
			
	        if(attr != null) 
	        {
	            String attrStr = attr.toString();
	            
	            if(attrStr.equals("titlePageSprite star-box-giga-star"))
	            {
	            	valueKey = "rating";
	            }
	        }
		}
		
		private void checkSpan(MutableAttributeSet a)
		{
			if(imdbData.getName() != null && imdbData.getRatingCount() != null)
				return;
				
			final Object class_attr = a.getAttribute(HTML.Attribute.CLASS);
			final Object itemprop_attr = a.getAttribute("itemprop");
			
	        if(itemprop_attr != null) 
	        {
	            String itempropStr = itemprop_attr.toString();
	            
	            if(itempropStr.equals("ratingCount"))
	            {
	            	valueKey = "ratingCount";
	            }
	            
	            if(imdbData.getName() == null && class_attr.toString().equals("itemprop") && itempropStr.equals("name"))
	            {
	            	valueKey = "name";
	            }
	        }
		}
	
	    public synchronized void handleEndOfLineString(String eol) 
	    {
	    	results.put(imdbData.getId(), imdbData);

    		count++;	

	    	if(results.size() % 150 == 0)
	    	{
	    		DataManager.storeToFile("imdbData.dat", results);
	    		System.out.println("------- Extracted Information: "+results.size());
	    	}
    		
	    	startExtraction();
	    }
	}

}
