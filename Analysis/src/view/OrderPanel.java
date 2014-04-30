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

public class OrderPanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	private static final String[] ORDERING = {"ASC", "DESC"};
	private Attribute[] attributes;
	
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
		setPreferredSize(new Dimension(150, 300));
	}
	
	private void createElements()
	{
		orderAttribute = new JComboBox<String>();
		ordering = new JComboBox<String>(ORDERING);		

		ConfigDialog.setComponentMaxHeight(orderAttribute);
		ConfigDialog.setComponentMaxHeight(ordering);
	}
	
	public void updateXAxis(XAxis xAxis)
	{
		orderAttribute.removeAllItems();
		
		switch(xAxis)
		{
			case Rating:attributes = RatingAttr.values();break;
			case User: 	attributes = UserAttr.values();break;
			case Movie: attributes = MovieAttr.values();break;
		}
		
		for(int i=0;i<attributes.length;i++)
		{
			orderAttribute.addItem(attributes[i].toString());
		}
		
		orderAttribute.revalidate();
		orderAttribute.repaint();
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

	public int getOrderAttrIndex()
	{
		return orderAttribute.getSelectedIndex();
	}
}
