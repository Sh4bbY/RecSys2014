package helper;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import model.ImdbData;

public class ImdbExtractor implements Runnable
{
	private final String baseUrl = "http://www.imdb.com/title/tt";
	
    private String valueKey = null;
    private HashMap<String,ImdbData> results;
    private ImdbData imdbData;
    private String[] imdbIDs;
    private int threadId;
    private int count;
    private static Long time = null;
    private int storeGap = 500;
    private int storeAt;
    
	public ImdbExtractor(String[] imdbIDs, HashMap<String, ImdbData> results, int count, int threadId)
	{		
		this.imdbIDs = imdbIDs;
		this.results = results;
		this.valueKey = "";
		this.count = count;
		this.threadId = threadId;
		this.storeAt = results.size() + storeGap;
		
		if(time == null)
		{
			time = System.currentTimeMillis();
		}
	}


	@Override
	public void run()
	{
		startExtraction();
	}

        
	public boolean startExtraction()
	{		
		String id = imdbIDs[count];
		
		if(results.size() >= imdbIDs.length)
		{
			if(this.threadId == 0)
			{
				DataManager.storeToFile("imdbData.dat", results);
			}
			return true;
		}
		
		while(results.containsKey(id))
		{
			id = imdbIDs[count];
			count++;
		}
		
		imdbData = new ImdbData(id);
		InputStream is;
        InputStreamReader isr;
        URL url;
        
		try
		{
			url = new URL(baseUrl + id);
			is = url.openStream();
            isr = new InputStreamReader(is);
            new ParserDelegator().parse(isr, new ExtractionCallback(imdbData), true);
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
	
		synchronized public void handleEndOfLineString(String eol) 
	    {
	    	results.put(imdbData.getId(), imdbData);

	    	if((results.size() > storeAt || results.size() == imdbIDs.length) && threadId == 0)
	    	{
	    		storeAt = results.size() + storeGap;
	    		DataManager.storeToFile("imdbData.dat", results);
	    		long currentTime = System.currentTimeMillis();
	    		float seconds = (currentTime - ImdbExtractor.time) / 1000;
	    		ImdbExtractor.time = currentTime;
	    		
	    		System.out.println("------- Thread["+threadId+"] -- Extracted Information: "+results.size()+"/"+imdbIDs.length+"   seconds needed = "+seconds);
	    	}
	    	
	    	if(results.size() < imdbIDs.length)
	    	{
		    	startExtraction();
	    	}
	    }
	}

}
