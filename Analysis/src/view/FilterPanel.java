package view;


import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

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
		filterAttribute = new JComboBox<String>(Analysis.ATTRIBUTES);
	}
	
	private void createListeners()
	{		
		
	}
	
	
	private void attachElements()
	{
		add(filterAttribute);
	}
}
