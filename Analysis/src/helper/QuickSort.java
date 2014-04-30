package helper;

import java.awt.Point;
import java.util.Stack;

public class QuickSort 
{		
	public void sort(double[][] values, int sortIndex, boolean isASC) 
	{
		Stack<Point> stack = new Stack<Point>();
		Point range;
		
		int i,j;
		double pivot,tmp;
		
		stack.push(new Point(0,values[0].length-1));
		
		while (!stack.isEmpty()) 
		{
			range = stack.pop();
			i = range.x;
			j = range.y;
			pivot = values[sortIndex][j];
			
			do 
			{
                while(isASC && values[sortIndex][i] < pivot || !isASC && values[sortIndex][i] > pivot) i++;
                while(isASC && values[sortIndex][j] > pivot || !isASC && values[sortIndex][j] < pivot) j--;
                
                if(i <= j) 
                {
    				for(int o=0;o<values.length;o++)
    				{
    					tmp = values[o][i];
    					values[o][i] = values[o][j];
    					values[o][j] = tmp;
    				}
                    i++;
                    j--;
                }
            } while(i <= j);
			
			
            if(range.x < j) 
            {   
                stack.push(new Point(range.x, j));  
            }
           
            if(i < range.y) 
            {
                stack.push(new Point(i, range.y));  
            }
			
		}
	}
}
