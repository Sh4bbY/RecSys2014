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

public class OrderPanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	private static final String[] ORDERING = {"ASC", "DESC"};
	
	private JComboBox<String> orderAttribute, ordering;
	
	public OrderPanel()
	{
		initialize();
		createElements();
		attachElements();
	}
	
	private void initialize()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder("Order"));
		setAlignmentY(JPanel.TOP_ALIGNMENT);
	}
	
	private void createElements()
	{
		orderAttribute = new JComboBox<String>();
		ordering = new JComboBox<String>(ORDERING);		
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
		
		orderAttribute = new JComboBox<String>(names);
	}
	
	private void attachElements()
	{
		add(orderAttribute);
		add(ordering);
	}
	
	public boolean isOrderASC()
	{
		return ordering.getSelectedIndex() == 0;
	}

	public int getOrderAttribute()
	{
		return orderAttribute.getSelectedIndex();
	}
}
