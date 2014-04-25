package view;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

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
		orderAttribute = new JComboBox<String>(Analysis.ATTRIBUTES);
		ordering = new JComboBox<String>(ORDERING);		
	}
	
	private void attachElements()
	{
		add(orderAttribute);
		add(ordering);
	}
}
