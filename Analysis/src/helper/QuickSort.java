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
		
		stack.push(new Point(0, values.length - 1));
		
		while (!stack.isEmpty()) 
		{
			range = stack.pop();
			i = range.x;
			j = range.y;
			pivot = values[j][sortIndex];
			
			do 
			{
                while(isASC && values[i][sortIndex] < pivot || !isASC && values[i][sortIndex] > pivot) i++;
                while(isASC && values[j][sortIndex] > pivot || !isASC && values[j][sortIndex] < pivot) j--;
                
                if(i <= j) 
                {
    				for(int o=0;o<values[0].length;o++)
    				{
    					tmp = values[i][o];
    					values[i][o] = values[j][o];
    					values[j][o] = tmp;
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
