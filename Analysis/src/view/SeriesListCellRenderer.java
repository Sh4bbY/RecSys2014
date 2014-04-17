package view;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class SeriesListCellRenderer extends JLabel implements ListCellRenderer
{
	private List<Integer> selections;
	public SeriesListCellRenderer(List<Integer> selections) 
	{
		this.selections = selections;
		setOpaque(true);
	}
	
	public Component getListCellRendererComponent(JList paramlist, Object value, int index, boolean isSelected, boolean cellHasFocus) 
	{
	    setText(value.toString());
	    
	    if (selections.contains(index)) 
	    {
	        setForeground(Color.blue);
	    } 
	    else
	    {
	        setForeground(Color.black);
	    }
	    return this;
	}
}