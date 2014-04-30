package view;


import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
import charting.attributes.XAxis;

public class FilterPanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	
	private JComboBox<String> filterAttribute;
	
	public FilterPanel()
	{
		createListeners();
		initialize();
		createElements();
		attachElements();
	}
	
	private void initialize()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder("Filter"));
		setAlignmentY(JPanel.TOP_ALIGNMENT);
		setPreferredSize(new Dimension(150, 300));
	}
	
	private void createElements()
	{
		filterAttribute = new JComboBox<String>();
		ConfigDialog.setComponentMaxHeight(filterAttribute);
	}
	
	private void createListeners()
	{		
		
	}
	
	
	private void attachElements()
	{
		add(filterAttribute);
	}
	
	public void updateXAxis(XAxis xAxis)
	{
		filterAttribute.removeAllItems();
		
		Attribute[] attributes = null;
		
		switch(xAxis)
		{
			case Rating: attributes = RatingAttr.values();break;
			case User: attributes = UserAttr.values();break;
			case Movie: attributes = MovieAttr.values();break;
		}
		
		for(int i=0;i<attributes.length;i++)
		{
			filterAttribute.addItem(attributes[i].toString());
		}
	}
}
