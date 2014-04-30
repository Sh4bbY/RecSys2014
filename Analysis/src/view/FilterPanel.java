package view;


import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
import charting.attributes.XAxis;
import main.Analysis;

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
	}
	
	private void createElements()
	{
		//filterAttribute = new JComboBox<String>(Analysis.ATTRIBUTES);
		updateAttributes(XAxis.Rating);
	}
	

	public void updateAttributes(XAxis xAxis)
	{
		Attribute[] attributes = null;
		
		switch(xAxis)
		{
			case Rating: attributes = RatingAttr.values();break;
			case User: attributes = UserAttr.values();break;
			case Movie: attributes = MovieAttr.values();break;
		}
		
		String[] names = new String[attributes.length];
		
		for(int i=0;i<attributes.length;i++)
		{
			names[i] = attributes[i].toString();
		}
		
		filterAttribute = new JComboBox<String>(names);
	}
	
	private void createListeners()
	{		
		
	}
	
	
	private void attachElements()
	{
		add(filterAttribute);
	}
}
