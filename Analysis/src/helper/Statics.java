package helper;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;


public class Statics
{
	public static final Dimension screenSize 	= Toolkit.getDefaultToolkit().getScreenSize();
	
	public static Point getCenterLocation(int width, int height)
	{
		return new Point((int) (screenSize.getWidth()/2 - width/2),(int) (screenSize.getHeight()/2 - height/2));
	}
	
	public static String loadResource(String path)
	{
		try 
		{
			URL url = ClassLoader.getSystemResource(path);
			return url.getPath();
		}
		catch(Exception err)
		{
		   System.err.println("Error at Resource image '"+path+"'");
		   err.printStackTrace();
		}
		
		return null;
	}
}
